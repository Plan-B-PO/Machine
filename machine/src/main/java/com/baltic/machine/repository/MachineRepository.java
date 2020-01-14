package com.baltic.machine.repository;

import com.baltic.machine.model.Machine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends MongoRepository<Machine, String> {

    Machine findByRunnableApplicationId(String id);

}