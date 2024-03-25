package com.example.tasktrackerhttp.dto;

import lombok.Data;

@Data
public abstract class EntityWithStatus extends AbstractEntity{
    private Status status;
}
