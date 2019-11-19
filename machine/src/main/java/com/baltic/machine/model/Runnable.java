package com.baltic.machine.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Runnable {
    String applicationId;
    ComputationSteps computationSteps;
    String version;
}
