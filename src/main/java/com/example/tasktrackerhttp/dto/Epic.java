package com.example.tasktrackerhttp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Epic extends AbstractEntity implements BelongsUser {

    private List<SubTask> subTasks;

    private String userName;

    public String getUserName () {
       return userName;
    }
    public void setUserName (String userName) {
        this.userName = userName;
    }
}


