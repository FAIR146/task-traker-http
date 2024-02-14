package com.example.tasktrackerhttp.controller.core.response;

import com.example.tasktrackerhttp.dto.Status;

public class PutSubTaskResponse {
    private long id;
    private String name;
    private Status status;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
