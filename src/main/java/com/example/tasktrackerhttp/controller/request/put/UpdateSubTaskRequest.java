package com.example.tasktrackerhttp.controller.request.put;

import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;

public class UpdateSubTaskRequest {
    private String name;
    private String description;
    private Status status;
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
