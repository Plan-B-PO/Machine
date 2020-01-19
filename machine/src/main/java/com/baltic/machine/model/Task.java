package com.baltic.machine.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static com.baltic.machine.model.ComputationStatus.CREATED;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    ComputationTask computationTask;
    String token;
    String computationId;
    ComputationStatus status = CREATED;

    @Override
    public String toString() {
        return "Task{" +
                "ComputationTask='" + computationTask + '\'' +
                "ComputationId='" + computationId + '\'' +
                ", token='" + token + '\'' +
                ", ComputationStatus=" + status +
                '}';
    }
}


