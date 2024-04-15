package com.example.tasktrackerhttp.controller.core.response;

import com.example.tasktrackerhttp.dto.SubTask;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class GetEpicResponse {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private long id;
    @NotBlank
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
