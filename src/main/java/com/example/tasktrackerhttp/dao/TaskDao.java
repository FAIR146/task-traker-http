package com.example.tasktrackerhttp.dao;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
    List<Epic> getEpicByStatusAndUserName(Status status, String username);

    // надо методами ниже, возможно, нужно подумать
//    List<Epic> getNewEpicByUserName(String username);
//    List<Epic> getInProgressEpicByUserName(String username);
//    List<Epic> getDoneEpicByUserName(String username);
}
