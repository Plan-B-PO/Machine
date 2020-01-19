package com.baltic.machine.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Aplication {
    String id;
    String name;
    String description;
    String icon;
    Schema schema;
}
