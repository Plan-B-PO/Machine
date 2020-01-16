package com.baltic.machine.controller;

import com.baltic.machine.model.ComputationTask;
import com.baltic.machine.service.enums.AbortStatus;
import com.baltic.machine.service.enums.ActivationStatus;
import com.baltic.machine.service.impl.ComputationTaskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/machine")
@RestController
public class MachineComputationController {

    private final ComputationTaskServiceImpl service;

    public MachineComputationController(ComputationTaskServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/computation/{id}")
    public ResponseEntity<ComputationTask> getComputationTask(@PathVariable String id) {
       ComputationTask computationTask = service.getComputationTask(id);
        System.out.println("get computation id: " + id);
        System.out.println("get computation task: " + computationTask);
       if (computationTask != null) {
           return ResponseEntity.ok(computationTask);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/computation")
    public ResponseEntity activateComputationTask(@Valid @RequestBody ComputationTask computationTask) throws InterruptedException {
        ActivationStatus status = service.activateComputationTask(computationTask);
        // TODO add all possibilities
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/computation/{id}")
    public ResponseEntity abortComputationTask(@PathVariable String id) {
        AbortStatus status = service.abortComputationTask(id);
        System.out.println("status in delete" + status);
        if (status == AbortStatus.EXITED_OK)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            // TODO even after killed - 404 NOT FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/changeStatus/{id}/{status}")
    public ResponseEntity changeStatus(@PathVariable String id, @PathVariable String status) {
        System.out.println("Container " + id + "\t status: " + status);
        service.changeTaskStatus(id, status);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/saveToLogger/{id}/{log}")
    public ResponseEntity saveToLogger(@PathVariable String id, @PathVariable String log) {
        System.out.println("Container " + id + "\t log: " + log);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
