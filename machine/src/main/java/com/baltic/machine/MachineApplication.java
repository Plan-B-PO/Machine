package com.baltic.machine;

import com.baltic.machine.model.Machine;
import com.baltic.machine.repository.MachineRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MachineApplication {
	public static void main(String[] args) {
		SpringApplication.run(MachineApplication.class, args);
	}


}
