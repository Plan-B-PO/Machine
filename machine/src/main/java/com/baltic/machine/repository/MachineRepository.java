package com.baltic.machine.repository;

import com.baltic.machine.model.Machine;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface MachineRepository extends MongoRepository<Machine, Optional> {

    Machine findByRunnableApplicationId(String id);
    List<Machine> findAllById();

}