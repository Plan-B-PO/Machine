package com.baltic.machine.controller;

import com.baltic.machine.model.Machine;
import com.baltic.machine.repository.MachineRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import org.bouncycastle.tsp.TSPUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
public class MachineComputationController {
    private final MachineRepository repository;
    private final DockerClient dockerClient;

    public MachineComputationController(MachineRepository repository) {
        this.repository = repository;
        this.dockerClient = DockerClientBuilder.getInstance("tcp://192.168.137.182:2375").build();
    }


    @GetMapping("/machine/computation/{id}")
    public ResponseEntity<Machine> getMachine(@PathVariable String id) {
       Machine machine = repository.findByRunnableApplicationId(id);
       if (machine != null) {
           return ResponseEntity.ok(machine);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/machine/computation")
    public ResponseEntity activateMachine(@RequestBody Machine newMachine) {
        String path = newMachine.getRunnable().getComputationSteps().getArtifactUrl();
        // TODO use Dockerfile

        String imageId = dockerClient.buildImageCmd()
                .withDockerfile(new File(path))
                .exec(new BuildImageResultCallback())
                .awaitImageId();

        CreateContainerResponse container
                = dockerClient.createContainerCmd(imageId).withTty(true).withAttachStdin(true).exec();
        dockerClient.startContainerCmd(container.getId()).exec();

        newMachine.getRunnable().setApplicationId(container.getId());
        repository.save(newMachine);
        System.out.println("container is running" + newMachine.toString());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/machine/computation/{id}")
    public ResponseEntity abortMachine(@PathVariable String id) {
        try {
            dockerClient.killContainerCmd(id).exec();
        } catch (ConflictException e) {
            System.out.println("Cannot kill");
        }
        InspectContainerResponse container
                = dockerClient.inspectContainerCmd(id).exec();

        // TODO
        if (container == null)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            // TODO even after killed - 404 NOT FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
