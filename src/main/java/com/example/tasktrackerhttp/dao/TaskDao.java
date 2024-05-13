package com.example.tasktrackerhttp.dao;

import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;

import java.util.List;


public interface TaskDao {
    long addTask (Task task);
    long addEpic (Epic epic);
    long addSubTask (SubTask subTask);
    void removeEpicById (long id);
    void removeSubTaskById (long id);
    void removeTaskById (long id);
    Epic getEpicById (long id);
    SubTask getSubTaskById (long id);
    Task getTaskById (long id);
    List<Task> getTasksByUsername(String name);
    List<Epic> getEpicByUsername(String name);
    void updateTask (Task task);
    void updateEpic (Epic epic);
    void updateSubTask (SubTask subTask);
    List<Task> getTaskByStatusAndUserName(Status status, String username);
    List<SubTask> getSubTasksByEpicId (long id);
}
