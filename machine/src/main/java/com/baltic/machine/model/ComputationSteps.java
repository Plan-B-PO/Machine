package com.baltic.machine.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputationSteps {
    private Params params;
    private List<String> artifactUrl;
    private String command;

}
