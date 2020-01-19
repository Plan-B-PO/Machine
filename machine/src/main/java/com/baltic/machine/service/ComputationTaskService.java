package com.baltic.machine.service;

import com.baltic.machine.model.Task;
import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.service.enums.AbortStatus;
import com.baltic.machine.service.enums.ActivationStatus;

public interface ComputationTaskService {
    Task getTask(String id);
    ActivationStatus activateComputationTask(Task task) throws InterruptedException;
    AbortStatus abortComputationTask(String id);
}
