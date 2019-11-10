package com.baltic.machine.controller;

import com.baltic.machine.model.MachineWithStatus;
import com.baltic.machine.repository.MachineWithStatusRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MachineManagerController {
    private final MachineWithStatusRepository machineRepository;

    public MachineManagerController(MachineWithStatusRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @PostMapping("/machine/running")
    private void runningMachinesPOST(@RequestBody MachineWithStatus newMachine) {
        machineRepository.deleteAll();
        machineRepository.save(newMachine);
    }

    @GetMapping("/machine/running/{userID}")
    private List<String> runningMachinesPOST(@PathVariable Long userID) {
        return machineRepository.findAllByMachineAppUserId(userID).stream().map(m ->  m.getMachine().getRunnable().getApplicationId()).collect(Collectors.toList());
    }

}
