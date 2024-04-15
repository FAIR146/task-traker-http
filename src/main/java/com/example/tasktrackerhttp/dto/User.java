package com.example.tasktrackerhttp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class User {
    @NotNull
    private final int id;
    @NotBlank
    private String name;

}
