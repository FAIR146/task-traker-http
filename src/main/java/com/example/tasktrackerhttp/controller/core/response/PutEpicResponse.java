package com.example.tasktrackerhttp.controller.core.response;

import jakarta.validation.constraints.NotNull;

public class PutEpicResponse {
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
