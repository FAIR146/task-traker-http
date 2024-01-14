package com.example.tasktrackerhttp.dao;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;

import java.util.List;


public interface TaskDao {
    long addTask (String name, String description, Status status);
    long addEpic (String name, String description);
    long addSubTask (long id ,String name, String description, Status status);
    void removeEpicById (long id);
    void removeSubTaskById (long id);
    void removeTaskById (long id);
    List<Task> getAllTasks ();
    List<Epic> getAllEpics ();
    List<SubTask> getAllSubTasks ();
    Epic getEpicById (long id);
    SubTask getSubTaskById (long id);
    Task getTaskById (long id);
    void updateTask (Task task);
    void updateEpic (Epic epic);
    void updateSubTask (SubTask subTask);

}
