package com.example.tasktrackerhttp.service;

import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Task;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class GetAllCreatedTasksByUser {
    private List<Task> inProgressTasks;
    private List<Task> newTasks;
    private List<Task> doneTasks;

}
