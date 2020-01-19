package com.baltic.machine.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ComputationStepPackage {
    String applicationId;
    List<ComputationSteps> computationSteps;
    String version;

}
