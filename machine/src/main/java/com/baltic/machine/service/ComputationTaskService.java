package com.baltic.machine.service;

import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.service.enums.AbortStatus;
import com.baltic.machine.service.enums.ActivationStatus;

public interface ComputationTaskService {
    ComputationTask getComputationTask(String id);
    ActivationStatus activateComputationTask(ComputationTask computationTask) throws InterruptedException;
    AbortStatus abortComputationTask(String id);
}
