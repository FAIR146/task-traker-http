package com.example.tasktrackerhttp.controller.core.put;

import com.example.tasktrackerhttp.dto.Status;
import jakarta.validation.constraints.NotNull;

public class PutTaskRequest extends AbstractPutRequest {
    @NotNull
    private Status status;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
