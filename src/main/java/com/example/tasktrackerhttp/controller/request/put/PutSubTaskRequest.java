package com.example.tasktrackerhttp.controller.request.put;

import com.example.tasktrackerhttp.dto.Status;

public class PutSubTaskRequest extends AbstractPutRequest {
    private int epicId;
    private Status status;

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
