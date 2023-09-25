package com.example.tasktrackerhttp.controller.request.put;

import com.example.tasktrackerhttp.dto.Status;

public class PutSubTaskRequest extends AbstractPutRequest {
    private long epicId;
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
