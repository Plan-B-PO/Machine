package com.baltic.machine.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputationTask {
//    Machine machine;
//    String token;
//    ComputationStatus status;
    String id;
    String name;
    String userId;
    Aplication aplication;
    Input input;
    ComputationStepPackage computationStepPackage;
    String token;
    String computationId;

    @Override
    public String toString() {
        return "ComputationTask{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", application='" + aplication + '\'' +
                ", input='" + input + '\'' +
                ", computationStepPackage='" + computationStepPackage + '\'' +
                ", token='" + token + '\'' +
                ", computationId='" + computationId + '\'' +
                '}';
    }

}