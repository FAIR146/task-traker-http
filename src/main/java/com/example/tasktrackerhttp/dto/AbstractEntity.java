package com.example.tasktrackerhttp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public abstract class AbstractEntity {

    private long id;
    private String name;
    private String description;
    private String userName;
}
