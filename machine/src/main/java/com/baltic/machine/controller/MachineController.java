package com.baltic.machine.controller;

import com.baltic.machine.model.Machine;
import com.baltic.machine.model.MachineStatus;
import com.baltic.machine.repository.MachineRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MachineController {
    private final MachineRepository repository;
    private final DockerClient dockerClient;

    public MachineController(MachineRepository repository) {
        this.repository = repository;
        this.dockerClient = DockerClientBuilder.getInstance("tcp://192.168.137.182:2375").build();
    }

    @GetMapping("/machine/status/{id}")
    public ResponseEntity<MachineStatus> machineStatus(@PathVariable Long id) {
        // TODO

        return new ResponseEntity<>(MachineStatus.healthy, HttpStatus.OK);
    }

    @GetMapping("/machine/computation/{id}")
    public ResponseEntity<Machine> getMachine(@PathVariable String id) {
        // TODO
       Machine machine = repository.findByApplicationId(id);
        // run docker container
        return ResponseEntity.ok(machine);
    }

    @PostMapping("/machine/computation")
    public ResponseEntity activateMachine(@RequestBody Machine newMachine) {
        repository.save(newMachine);
        // TODO use Dockerfile
        CreateContainerResponse container = dockerClient.createContainerCmd("busybox")
                .withCmd("touch", "/test")
                .exec();
        dockerClient.startContainerCmd(container.getId()).exec();
        System.out.println( "Started" + container.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity abortMachine(@RequestBody String name) {
        // TODO
        // run docker container
        dockerClient.killContainerCmd(name).exec();
        InspectContainerResponse container
                = dockerClient.inspectContainerCmd(name).exec();
        if(container == null)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}