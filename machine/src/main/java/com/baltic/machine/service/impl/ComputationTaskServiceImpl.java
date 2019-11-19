package com.baltic.machine.service.impl;

import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.model.Machine;
import com.baltic.machine.repository.MachineRepository;
import com.baltic.machine.service.ComputationTaskService;
import com.baltic.machine.service.enums.ActivationStatus;
import com.baltic.machine.service.enums.AbortStatus;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.File;

@Service
public class ComputationTaskServiceImpl implements ComputationTaskService {

    private final MachineRepository repository;
    @Value("${docker.connector.value}")
    private String connector;
    private final DockerClient dockerClient;

    public ComputationTaskServiceImpl(@Value("${docker.connector.value}") String connector, MachineRepository repository) {
        this.dockerClient = DockerClientBuilder.getInstance(connector).build();
        this.repository = repository;
    }

    public Machine getComputationTask(String id) {
        return repository.findByRunnableApplicationId(id);
    }

    public ActivationStatus activateComputationTask(ComputationTask computationTask) {
        String path = computationTask.getMachine().getRunnable().getComputationSteps().getArtifactUrl();
        // TODO use Dockerfile, docker hub

        String imageId = dockerClient.buildImageCmd()
                .withDockerfile(new File(path))
                .exec(new BuildImageResultCallback())
                .awaitImageId();

        CreateContainerResponse container
                = dockerClient.createContainerCmd(imageId).withTty(true).withAttachStdin(true).exec();
        dockerClient.startContainerCmd(container.getId()).exec();

        Machine machine = computationTask.getMachine();
        machine.getRunnable().setApplicationId(container.getId());
        repository.save(computationTask.getMachine());
        System.out.println("container is running" + machine.toString());
        return ActivationStatus.ACTIVATED_OK;
    }

    public AbortStatus abortComputationTask(String id) {
        try {
            dockerClient.killContainerCmd(id).exec();
        } catch (ConflictException e) {
            System.out.println("Cannot kill");
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
}
