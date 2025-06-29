package com.task.services.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner {

    private String id;
    private String name;
    private String accountNumber;
    private int level;
}
