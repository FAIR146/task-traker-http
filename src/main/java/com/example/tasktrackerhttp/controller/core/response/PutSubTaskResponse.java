package com.example.tasktrackerhttp.controller.core.response;

import com.example.tasktrackerhttp.dto.Status;
import jakarta.validation.constraints.NotNull;

public class PutSubTaskResponse {
    @NotNull
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
