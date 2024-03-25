package com.example.tasktrackerhttp.dto;



public class Task extends EntityWithStatus implements BelongsUser{
    private String userName;
    public String getUserName () {
        return userName;
    }
    public void setUserName (String userName) {
        this.userName = userName;
    }
}


