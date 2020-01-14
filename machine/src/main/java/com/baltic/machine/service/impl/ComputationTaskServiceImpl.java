package com.baltic.machine.service.impl;

import com.baltic.machine.model.ComputationStatus;
import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.repository.ComputationTaskRepository;
import com.baltic.machine.service.ComputationTaskService;
import com.baltic.machine.service.enums.ActivationStatus;
import com.baltic.machine.service.enums.AbortStatus;
import com.baltic.machine.service.enums.StepStatus;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


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

    public ActivationStatus activateComputationTask(ComputationTask computationTask) {
        System.out.println(computationTask);
        List<String> containerFromDockerHub = null;
        UUID uuid = UUID.randomUUID();

        try {
             containerFromDockerHub = computationTask.getMachine().getRunnable().getComputationSteps().getArtifactUrl();
        } catch (Exception e) {
            System.out.println(e);
            containerFromDockerHub.add("busybox:latest");
        }

        // TODO use Dockerfile, docker hub
        // TODO image from dockerfile
//        String imageId = dockerClient.buildImageCmd()
//                .withDockerfile(new File(path))
//                .exec(new BuildImageResultCallback())
//                .awaitImageId();
        Volume volume = new Volume("/opt/computations");
        String appUser =  computationTask.getMachine().getAppUserId();
        for(int i = 0; i < containerFromDockerHub.size(); i++) {
            String containerName = containerFromDockerHub.get(i);
            CreateContainerResponse container
                = dockerClient.createContainerCmd(containerName)
                .withName(appUser + "_"+ uuid + "_" + i)
                .withTty(true)
                .withAttachStdin(true)
                .withVolumes(volume)
                .exec();
        dockerClient.startContainerCmd(container.getId()).exec();

        computationTask.setStatus(ComputationStatus.CREATED);
        computationTask.getMachine().getRunnable().setApplicationId(container.getId());
        computationTaskRepository.save(computationTask);
        System.out.println("container is running: " + computationTask.toString());
        }

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
        ComputationTask computationTask;
        try {
            computationTask = computationTaskRepository.findByMachineRunnableApplicationId(id);
            computationTask.setStatus(ComputationStatus.STEP_FINISHED);
            computationTaskRepository.save(computationTask);
        } catch (Exception e) {

        }

        return StepStatus.OK;
    }
}
