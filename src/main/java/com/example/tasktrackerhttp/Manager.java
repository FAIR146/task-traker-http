package com.example.tasktrackerhttp;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Manager  {
    private final TaskDao taskDao = new InMemoryTaskDao();

    public long addTask(String name, String description, Status status) {
        return taskDao.addTask(name,description,status);
    }

    public long addEpic(String name, String description) {
        return taskDao.addEpic(name, description);
    }

    public long addSubTask(long id, String name, String description, Status status) {
        return taskDao.addSubTask(id, name, description, status);
    }

    public void removeEpicById(long id) {
        taskDao.removeEpicById(id);
    }

    public void removeTaskById(long id) {
        taskDao.removeTaskById(id);
    }

    public void removeSubTaskById(long id) {
        taskDao.removeSubTaskById(id);
    }

    public void removeAllEpics() {
        taskDao.removeAllEpics();
    }

    public void removeAllSubTasks() {
        taskDao.removeAllSubTasks();
    }

    public void removeAllTasks() {
        taskDao.removeAllTasks();
    }

    public Epic getEpicById(long id) {
        return taskDao.getEpicById(id);
    }

    public Task getTaskById(long id) {
        return taskDao.getTaskById(id);
    }

    public SubTask getSubTaskById(long id) {
        return taskDao.getSubTaskById(id);
    }

    public List<Epic> getAllEpics() {
        return taskDao.getAllEpics();
    }

    public List<SubTask> getAllSubTasks() {
        return taskDao.getAllSubTasks();
    }

    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }
    public void updateTask (long id, String name, String description, Status status) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        task.setId(id);
        taskDao.updateTask(task);
    }
    public void updateEpic (long id, String name, String description) {
        Epic epic = new Epic();
        List<SubTask> subTasks = taskDao.getEpicById(id).getSubTasks();
        epic.setName(name);
        epic.setDescription(description);
        epic.setId(id);
        for (SubTask subTask:subTasks) {
            epic.addSubTask(subTask);
        }
        taskDao.updateEpic(epic);
    }
    public void updateSubTask (long id, String name, String description, Status status) {
        SubTask subTask = new SubTask(getEpicById(id));
        subTask.setDescription(description);
        subTask.setStatus(status);
        subTask.setName(name);
        taskDao.updateSubTask(subTask);
    }

}

