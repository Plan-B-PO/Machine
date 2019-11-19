package com.baltic.machine.controller;

import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.model.Machine;
import com.baltic.machine.service.enums.AbortStatus;
import com.baltic.machine.service.enums.ActivationStatus;
import com.baltic.machine.service.impl.ComputationTaskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/machine")
@RestController
public class MachineComputationController {

    private final ComputationTaskServiceImpl service;

    public MachineComputationController(ComputationTaskServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/computation/{id}")
    public ResponseEntity<Machine> getComputationTask(@PathVariable String id) {
       Machine machine = service.getComputationTask(id);
       if (machine != null) {
           return ResponseEntity.ok(machine);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

        @PostMapping("/computation")
    public ResponseEntity activateComputationTask(@RequestBody ComputationTask computationTask) {
        ActivationStatus status = service.activateComputationTask(computationTask);
        // TODO add all possibilities
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/computation/{id}")
    public ResponseEntity abortComputationTask(@PathVariable String id) {
        AbortStatus status = service.abortComputationTask(id);
        if (status == AbortStatus.EXITED_OK)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            // TODO even after killed - 404 NOT FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
