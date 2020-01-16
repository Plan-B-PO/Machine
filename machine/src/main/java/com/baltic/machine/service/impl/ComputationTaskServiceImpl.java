package com.baltic.machine.service.impl;

import com.baltic.machine.model.ComputationStatus;
import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.repository.ComputationTaskRepository;
import com.baltic.machine.service.ComputationTaskService;
import com.baltic.machine.service.enums.ActivationStatus;
import com.baltic.machine.service.enums.AbortStatus;
import com.baltic.machine.service.enums.StepStatus;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.github.dockerjava.core.exec.WaitContainerCmdExec;
import com.sun.tools.javac.main.CommandLine;
import org.apache.tomcat.jni.OS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class ComputationTaskServiceImpl implements ComputationTaskService {

    private final ComputationTaskRepository computationTaskRepository;
    @Value("${docker.connector.value}")
    private String connector;
    private final DockerClient dockerClient;

    public ComputationTaskServiceImpl(@Value("${docker.connector.value}") String connector, ComputationTaskRepository repository) {
        this.dockerClient = DockerClientBuilder.getInstance(connector).build();
        this.computationTaskRepository = repository;
    }

    public ComputationTask getComputationTask(String id) {
        return computationTaskRepository.findByMachineRunnableApplicationId(id);
    }

    public ActivationStatus activateComputationTask(ComputationTask computationTask) throws InterruptedException {
        System.out.println(computationTask);
        List<String> containerFromDockerHub = null;
        UUID uuid = UUID.randomUUID();

        try {
             containerFromDockerHub = computationTask.getMachine().getRunnable().getComputationSteps().getArtifactUrl();
        } catch (Exception e) {
            System.out.println(e);
            containerFromDockerHub.add("busybox:latest");
        }

        List<String> finalContainerFromDockerHub = containerFromDockerHub;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
        HostConfig hostConfig = HostConfig.newHostConfig()
                .withBinds(Bind.parse("/root/pop_image/computations/:/opt/computations"));



        String appUser =  computationTask.getMachine().getAppUserId();
        for(int i = 0; i < finalContainerFromDockerHub.size(); i++) {
            String containerName = finalContainerFromDockerHub.get(i);
            CreateContainerResponse container
                = dockerClient.createContainerCmd(containerName)
                .withName(appUser + "_"+ uuid + "_" + i)
                .withTty(true)
                .withAttachStdin(true).withHostConfig(hostConfig)
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();

        computationTask.setStatus(ComputationStatus.CREATED);
        computationTask.getMachine().getRunnable().setApplicationId(container.getId());
        computationTaskRepository.save(computationTask);

            int status = 0;
            try {
                status = dockerClient.waitContainerCmd(container.getId())
                    .exec(new WaitContainerResultCallback()).awaitCompletion().awaitStatusCode();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("status " + i + " " + status);

        System.out.println("container is running: " + computationTask.toString());

        }

            }
        });
        thread.start();
        return ActivationStatus.ACTIVATED_OK;

    }

    public AbortStatus abortComputationTask(String id) {
        System.out.println("Requested to kill container id: " + id);

        try {
            ComputationTask computationTask = computationTaskRepository.findByMachineRunnableApplicationId(id);
            id = computationTask.getMachine().getRunnable().getApplicationId();
            System.out.println("Requested to kill container id: " + id);
            computationTask.setStatus(ComputationStatus.DONE);
            computationTaskRepository.save(computationTask);
            dockerClient.killContainerCmd(id).exec();
//            dockerClient.removeContainerCmd(id).exec();
        } catch (Exception e) {
            System.out.println("Cannot kill");
            System.out.println(e);
        }
        InspectContainerResponse container
                = dockerClient.inspectContainerCmd(id).exec();

        // TODO
        if (container == null)
            return AbortStatus.EXITED_OK;
        else
            // TODO even after killed - 404 NOT FOUND
            return AbortStatus.EXITED_NOK;
    }

    public StepStatus changeTaskStatus(String id, String status) {
        ComputationTask computationTask = null;
        try {
            computationTask = computationTaskRepository.findByMachineRunnableApplicationId(id);
            computationTask.setStatus(ComputationStatus.valueOf (status));
            computationTaskRepository.save(computationTask);
        } catch (Exception e) {
            System.out.println("changeTaskStatus" + e);

        }

        return StepStatus.OK;
    }


}
