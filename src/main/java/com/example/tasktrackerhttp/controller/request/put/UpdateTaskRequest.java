package com.example.tasktrackerhttp.controller.request.put;

import com.example.tasktrackerhttp.dto.Status;

public class UpdateTaskRequest {
    private String name;
    private String description;
    private long id;
    private Status status;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
