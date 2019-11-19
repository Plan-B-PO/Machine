package com.baltic.machine.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Machine {
    String id;
    String appUserId;
    Runnable runnable;
    String logger;


    @Override
    public String toString() {
        return "Machine{" +
                "id='" + id + '\'' +
                ", appUser='" + appUserId + '\'' +
                ", runnable=" + runnable +
                ", logger='" + logger + '\'' +
                '}';
    }
}
