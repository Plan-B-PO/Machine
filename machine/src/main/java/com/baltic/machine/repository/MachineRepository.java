package com.baltic.machine.repository;

import com.baltic.machine.model.Machine;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface MachineRepository extends MongoRepository<Machine, String> {

    Machine findByRunnableApplicationId(String id);

}