package com.example.tasktrackerhttp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public abstract class AbstractEntity {
    @NotNull
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private String userName;
}
