package com.baltic.machine.repository;

import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputationTaskRepository extends MongoRepository<Task, String> {
    //Task findByMachineId(String id);
    //Task findByMachineRunnableApplicationId(String name);
    Task findByComputationTaskApplicationId(String name);

}
