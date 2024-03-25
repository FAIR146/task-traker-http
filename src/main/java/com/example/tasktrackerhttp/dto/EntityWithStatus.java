package com.example.tasktrackerhttp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class EntityWithStatus extends AbstractEntity{
    private Status status;
}
