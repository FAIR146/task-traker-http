package com.example.tasktrackerhttp.controller.core.put;

import jakarta.validation.constraints.NotNull;

public class PutEpicRequest extends AbstractPutRequest {

    @NotNull
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
