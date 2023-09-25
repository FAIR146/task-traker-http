package com.example.tasktrackerhttp.dao;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskDao implements TaskDao {
    private final HashMap<Long, Task> tasks = new HashMap<>();
    private final HashMap<Long, Epic> epics = new HashMap<>();
    private final HashMap<Long, SubTask> subTasks = new HashMap<>();

    private long idGeneratorTask = 0;
    private long idGeneratorEpic = 0;
    private long idGeneratorSubTask = 0;

    @Override
    public long addTask(String name, String description, Status status) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        task.setId(idGeneratorTask);
        tasks.put(idGeneratorTask, task);
        idGeneratorTask++;
        return task.getId();
    }

    @Override
    public long addEpic(String name, String description) {
        Epic epic = new Epic();
        epic.setName(name);
        epic.setDescription(description);
        epic.setId(idGeneratorEpic);
        epics.put(idGeneratorEpic, epic);
        idGeneratorEpic++;
        return epic.getId();
    }

    @Override
    public long addSubTask(long idEpic, String name, String description, Status status) {
        Epic epic = getEpicById(idEpic);
        SubTask subTask = new SubTask(epic);
        subTask.setDescription(description);
        subTask.setStatus(status);
        subTask.setName(name);
        subTask.setId(idGeneratorSubTask);
        subTasks.put(idGeneratorSubTask, subTask);
        epic.addSubTask(subTask);
        idGeneratorSubTask++;
        return subTask.getId();
    }

    @Override
    public void removeEpicById(long id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            epic.getSubTasks().forEach(subTask -> subTasks.remove(subTask.getId()));
            epics.remove(id);
        }
    }

    @Override
    public void removeSubTaskById(long id) {
        SubTask subTask = subTasks.get(id);
        if(subTask != null) {
            subTasks.remove(id);
        }
    }

    @Override
    public void removeTaskById(long id) {
        Task task = tasks.get(id);
        if (task != null) {
            tasks.remove(id);
        }
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
    }

    @Override
    public void removeAllSubTasks() {
        subTasks.clear();
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public Epic getEpicById(long id) {
        return epics.get(id);
    }

    @Override
    public SubTask getSubTaskById(long id) {
        return subTasks.get(id);
    }

    @Override
    public Task getTaskById(long id) {
        return tasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(),epic);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

}


