package com.baltic.machine.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Application {
    String id;
    String name;
    String description;
    String icon;
    List<Schema> schema;
}
