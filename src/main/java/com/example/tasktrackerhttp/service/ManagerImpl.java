package com.example.tasktrackerhttp.service;
import com.example.tasktrackerhttp.dao.TaskDao;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerImpl implements Manager {

    private final TaskDao taskDao;
    public ManagerImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }


    public long addTask(String name, String description, Status status) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        return taskDao.addTask(task);
    }

    public long addEpic(String name, String description) {
        Epic epic = new Epic();
        epic.setName(name);
        epic.setDescription(description);
        return taskDao.addEpic(epic);
    }

    //TODO: не хватает связи эпик -> сабтаск. То есть сабтаск-> эпик есть, а наоборот связь не выставлена
    public long addSubTask (long epicId, String name, String description, Status status) {
        SubTask subTask = new SubTask();
        subTask.setEpicId(epicId);
        subTask.setName(name);
        subTask.setDescription(description);
        subTask.setStatus(status);
        return taskDao.addSubTask(subTask);
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
//        Epic epic = new Epic();
//        List<SubTask> subTasks = taskDao.getEpicById(id).getSubTasksId();
//        epic.setName(name);
//        epic.setDescription(description);
//        epic.setId(id);
//        for (SubTask subTask:subTasks) {
//            epic.addSubTask(subTask);
//        }
//        taskDao.updateEpic(epic);
    }
    public void updateSubTask (long id, String name, String description, Status status) {
//        SubTask subtaskForUpdate = taskDao.getSubTaskById(id);
//        SubTask subTask = new SubTask(getEpicById(subtaskForUpdate.getEpic().getId()));
//
//        subTask.setDescription(description);
//        subTask.setStatus(status);
//        subTask.setName(name);
//        subTask.setId(subtaskForUpdate.getId());
//        taskDao.updateSubTask(subTask);
    }

}

