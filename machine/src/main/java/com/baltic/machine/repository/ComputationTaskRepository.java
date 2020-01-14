package com.baltic.machine.repository;

import com.baltic.machine.model.ComputationTask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputationTaskRepository extends MongoRepository<ComputationTask, String> {
    ComputationTask findByMachineId(String id);
    ComputationTask findByMachineRunnableApplicationId(String name);
}
