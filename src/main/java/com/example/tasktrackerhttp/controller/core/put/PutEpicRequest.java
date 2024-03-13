package com.example.tasktrackerhttp.controller.core.put;

public class PutEpicRequest extends AbstractPutRequest {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
