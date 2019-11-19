package com.baltic.machine.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputationSteps {
    private Params params;
    private String artifactUrl;
    private String command;

}
