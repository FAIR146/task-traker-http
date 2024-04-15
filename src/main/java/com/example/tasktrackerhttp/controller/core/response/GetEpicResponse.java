package com.example.tasktrackerhttp.controller.core.response;


import java.util.List;

public class GetEpicResponse {
    private String name;
    private String description;
    private long id;
    private String userName;

    private List<GetSubTaskResponse> subTasks;

    public List<GetSubTaskResponse> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<GetSubTaskResponse> subTasksResponse) {
        this.subTasks = subTasksResponse;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
