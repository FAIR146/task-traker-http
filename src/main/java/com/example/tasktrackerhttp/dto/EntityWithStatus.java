package com.example.tasktrackerhttp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class EntityWithStatus extends AbstractEntity{
    @NotBlank
    private Status status;
}
