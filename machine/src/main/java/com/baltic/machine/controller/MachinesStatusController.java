package com.baltic.machine.controller;

import com.baltic.machine.model.MachineWithStatus;
import com.baltic.machine.repository.ContainerStatus;
import com.baltic.machine.repository.MachineRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MachinesStatusController {

    private final MachineRepository machineRepository;
    private final DockerClient dockerClient;

    public MachinesStatusController(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
        this.dockerClient = DockerClientBuilder.getInstance("tcp://192.168.137.182:2375").build();
    }

    @GetMapping("/machine/running")
    private void runningMachines()
    {
        System.out.println("runningMachines");
        final String uri = "http://localhost:8080/machine/running";
        List<Container> containers = dockerClient.listContainersCmd().exec();
        containers.forEach(m -> System.out.println(m.getId()));

        RestTemplate restTemplate = new RestTemplate();
        machineRepository.findAll().stream().forEach(m -> restTemplate.postForObject(uri, new MachineWithStatus(m, ContainerStatus.RUNNiNG), MachineWithStatus.class));


    }

}
