package com.example.tasktrackerhttp.controller.request.put;

import com.example.tasktrackerhttp.dto.Status;

public class PutTaskRequest extends AbstractPutRequest {
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
