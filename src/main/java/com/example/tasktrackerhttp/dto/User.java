package com.example.tasktrackerhttp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class User {

    private final int id;
    @NotBlank
    private String name;

}
