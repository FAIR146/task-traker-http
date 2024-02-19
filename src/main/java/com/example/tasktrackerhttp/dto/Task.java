package com.example.tasktrackerhttp.dto;

import lombok.Data;

@Data
public class Task {
    private long id;
    private String userName;
    private String name;
    private String description;
    private Status status;

}


