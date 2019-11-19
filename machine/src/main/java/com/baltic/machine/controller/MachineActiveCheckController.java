package com.baltic.machine.controller;

import com.baltic.machine.model.ComputationStatus;
import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.model.Resource;
import com.baltic.machine.repository.MachineRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DockerClientBuilder;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.List;

@RestController
public class MachineActiveCheckController {

    private final MachineRepository machineRepository;
    private final DockerClient dockerClient;
    @Value("${docker.connector.value}")
    private String connector;
    @Value("${machineManager.activeCheck.computationTask.url}")
    private String activeCheckStatusUrl;
    @Value("${machineManager.activeCheck.load.url}")
    private String activeCheckLoadUrl;
    @Value("${machine.token}")
    private String token;

    public MachineActiveCheckController(@Value("${docker.connector.value}") String connector, MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
        this.dockerClient = DockerClientBuilder.getInstance(connector).build();
    }

    @GetMapping("/machine/running")
    private void runningMachines()
    {
        List<Container> containers = dockerClient.listContainersCmd().exec();
        containers.forEach(m -> System.out.println(m.getId()));

        RestTemplate restTemplate = new RestTemplate();
        machineRepository.findAll().forEach(m -> restTemplate.postForObject(activeCheckStatusUrl, new ComputationTask(m, token, ComputationStatus.RUNNING), HttpEntity.class));
    }

    @GetMapping("/machine/load")
    private void loadCheck()
    {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        Resource resource = new Resource(memoryMXBean.getHeapMemoryUsage().getInit() /1073741824D, 1D, 1D, 1D);
        System.out.println(resource);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(activeCheckLoadUrl, resource, Resource.class);
    }
}
