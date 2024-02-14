package com.example.tasktrackerhttp.service;

import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;

import java.util.List;

public interface Manager {
    long addTask (String name, String description, Status status);

    long addEpic(String name, String description);

    long addSubTask(long epicId, String name, String description, Status status);

    void removeEpicById(long id);

    void removeTaskById(long id);

    void removeSubTaskById(long id);
    Epic getEpicById(long id);

    Task getTaskById(long id);

    SubTask getSubTaskById(long id);
    void updateTask (long id, String name, String description, Status status);
    void updateEpic (long id, String name, String description);
    void updateSubTask (long id, String name, String description, Status status); // long epicId
    GetAllCreatedTasksByUser getAllCreatedTasksByUser(String userName);
}
