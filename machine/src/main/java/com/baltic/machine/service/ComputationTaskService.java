package com.baltic.machine.service;

import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.model.Machine;
import com.baltic.machine.service.enums.AbortStatus;
import com.baltic.machine.service.enums.ActivationStatus;

public interface ComputationTaskService {
    Machine getComputationTask(String id);
    ActivationStatus activateComputationTask(ComputationTask computationTask);
    AbortStatus abortComputationTask(String id);
}
