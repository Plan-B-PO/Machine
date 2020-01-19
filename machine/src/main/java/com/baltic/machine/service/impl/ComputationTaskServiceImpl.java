package com.baltic.machine.service.impl;

import com.baltic.machine.model.ComputationSteps;
import com.baltic.machine.model.Task;
import com.baltic.machine.model.ComputationStatus;
import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.repository.ComputationTaskRepository;
import com.baltic.machine.service.ComputationTaskService;
import com.baltic.machine.service.enums.ActivationStatus;
import com.baltic.machine.service.enums.AbortStatus;
import com.github.dockerjava.api.DockerClient;
import com.baltic.machine.service.enums.StepStatus;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


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

    public Task getTask(String id) {
        return computationTaskRepository.findByComputationTaskApplicationId(id);
    }

    public ActivationStatus activateComputationTask(Task task) throws InterruptedException {
        System.out.println(task);
        List<String> containerFromDockerHub = null;
        UUID uuid = UUID.randomUUID();

        try {
//             containerFromDockerHub = computationTask.getMachine().getRunnable().getComputationSteps().getArtifactUrl();
            List<ComputationSteps> listOfComputationSteps = task.getComputationTask().getComputationStepPackage().getComputationSteps();
            List<String> listOfArtifactUrl = null;

            System.out.println(listOfComputationSteps);

            listOfComputationSteps.forEach( step -> System.out.println(step.getArtifactUrl()));


            containerFromDockerHub = listOfComputationSteps.stream()
                    .map(ComputationSteps::getArtifactUrl).filter(s -> s != null)
                    .collect(toList());


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



        String appUser =  task.getComputationTask().getUserId();
        for(int i = 0; i < finalContainerFromDockerHub.size(); i++) {
            //String containerName = finalContainerFromDockerHub.get(i);
            String containerName = task.getComputationTask().getComputationStepPackage().getApplicationId();
            CreateContainerResponse container
                = dockerClient.createContainerCmd(containerName)
                .withName(appUser + "_"+ uuid + "_" + i)
                .withTty(true)
                .withAttachStdin(true).withHostConfig(hostConfig)
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();

        task.setStatus(ComputationStatus.CREATED);
        task.getComputationTask().getComputationStepPackage().setApplicationId(container.getId());
        computationTaskRepository.save(task);

            int status = 0;
            try {
                status = dockerClient.waitContainerCmd(container.getId())
                    .exec(new WaitContainerResultCallback()).awaitCompletion().awaitStatusCode();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("status " + i + " " + status);

        System.out.println("container is running: " + task.getComputationTask().toString());

        }

            }
        });
        thread.start();
        return ActivationStatus.ACTIVATED_OK;

    }

    public AbortStatus abortComputationTask(String id) {
        System.out.println("Requested to kill container id: " + id);

        try {
            Task task = computationTaskRepository.findByComputationTaskApplicationId(id);
            id = task.getComputationTask().getComputationStepPackage().getApplicationId();
            System.out.println("Requested to kill container id: " + id);
            task.setStatus(ComputationStatus.DONE);
            computationTaskRepository.save(task);
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
        //ComputationTask computationTask = null;
        Task task = null;
        try {
            task = computationTaskRepository.findByComputationTaskApplicationId(id);
            task.setStatus(ComputationStatus.valueOf (status));
            computationTaskRepository.save(task);
        } catch (Exception e) {
            System.out.println("changeTaskStatus" + e);

        }

        return StepStatus.OK;
    }


}
