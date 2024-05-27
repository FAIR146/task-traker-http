package com.example.tasktrackerhttp.dto;


import jakarta.validation.constraints.NotBlank;

public class Task extends EntityWithStatus implements BelongsUser{

    private String userName;
    public String getUserName () {
        return userName;
    }
    public void setUserName (String userName) {
        this.userName = userName;
    }
}


