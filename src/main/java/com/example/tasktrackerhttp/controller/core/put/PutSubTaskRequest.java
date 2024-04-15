package com.example.tasktrackerhttp.controller.core.put;

import com.example.tasktrackerhttp.dto.Status;
import jakarta.validation.constraints.NotNull;

public class PutSubTaskRequest extends AbstractPutRequest {
    @NotNull
    private Long epicId;
    @NotNull
    private Status status;


    public long getEpicId() {
        return epicId;
    }
    public void setEpicId(long epicId) {
        this.epicId = epicId;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
