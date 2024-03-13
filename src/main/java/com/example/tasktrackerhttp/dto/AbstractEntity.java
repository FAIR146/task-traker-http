package com.example.tasktrackerhttp.dto;

import lombok.Data;

@Data
public abstract class AbstractEntity {
    private long id;
    private String name;
    private String description;
    private String userName;
}
