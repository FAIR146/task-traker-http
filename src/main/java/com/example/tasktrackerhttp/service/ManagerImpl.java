package com.example.tasktrackerhttp.service;
import com.example.tasktrackerhttp.dao.TaskDao;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import com.example.tasktrackerhttp.service.dto.GetAllCreatedEpicsByUser;
import com.example.tasktrackerhttp.service.dto.GetAllCreatedTasksByUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ManagerImpl implements Manager {

    private final TaskDao taskDao;
    public ManagerImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }


    public long addTask (String name, String description, Status status, String userName) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        task.setUserName(userName);

        long id = taskDao.addTask(task);
        log.debug("Task = [{}] was created with id = [{}] for user = [{}}", task, id, userName);

        return id;
    }

    public long addEpic(String name, String description, String userName) {
        Epic epic = new Epic();
        epic.setName(name);
        epic.setDescription(description);
        epic.setUserName(userName);

        long id = taskDao.addEpic(epic);
        log.debug("Epic = [{}] was created with id = [{}] for user = [{}]", epic, id, userName);

        return id;
    }

    //TODO: не хватает связи эпик -> сабтаск. То есть сабтаск-> эпик есть, а наоборот связь не выставлена
    public long addSubTask (long epicId, String name, String description, Status status) {
        SubTask subTask = new SubTask();
        subTask.setEpicId(epicId);
        subTask.setName(name);
        subTask.setDescription(description);
        subTask.setStatus(status);

        long id = taskDao.addSubTask(subTask);
        log.debug("SubTask = [{}] was created with id = [{}]", subTask, id);

        return id;
    }

    public void removeEpicById(long id) {
        taskDao.removeEpicById(id);
        log.debug("Remove epic with id = [{}]", id);

    }

    public void removeTaskById(long id) {
        taskDao.removeTaskById(id);
        log.debug("Remove task with id = [{}]", id);

    }

    public void removeSubTaskById(long id) {
        taskDao.removeSubTaskById(id);
        log.debug("Remove SubTask with id = [{}]", id);
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

    public void updateTask (long id, String name, String description, Status status) {
        Task task = new Task();
        log.debug("Update Task with id = [{}]. Old value = [{}]. New value SubTask: name = [{}] description = [{}] status = [{}].", id, task, name, description, status);
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        taskDao.updateTask(task);
    }
    public void updateEpic (long id, String name, String description) {
        Epic epic = new Epic();
        log.debug("Update Epic with id = [{}]. Old value = [{}]. New value SubTask: name = [{}] description = [{}].", id, epic, name, description);
        epic.setId(id);
        List<SubTask> subTasks = taskDao.getEpicById(id).getSubTasks();
        epic.setName(name);
        epic.setDescription(description);
        epic.setSubTasks(subTasks);
        taskDao.updateEpic(epic);
    }
    public void updateSubTask (long id, String name, String description, Status status) { //long epicId
        SubTask subTask = taskDao.getSubTaskById(id);
        log.debug("Update SubTask with id = [{}]. Old value = [{}]. New value SubTask: name = [{}] description = [{}] status = [{}].", id, subTask, name, description, status);
        subTask.setId(id);
        subTask.setName(name);
        subTask.setDescription(description);
        subTask.setStatus(status);
        taskDao.updateSubTask(subTask);
    }

    @Override
    public GetAllCreatedTasksByUser getAllCreatedTasksByUser(String userName) {
        List<Task> inProgressTasks = taskDao.getTaskByStatusAndUserName(Status.IN_PROGRESS, userName);
        List<Task> newTasks = taskDao.getTaskByStatusAndUserName(Status.NEW, userName);
        List<Task> doneTasks = taskDao.getTaskByStatusAndUserName(Status.DONE, userName);

        return GetAllCreatedTasksByUser.builder()
                .newTasks(newTasks)
                .inProgressTasks(inProgressTasks)
                .doneTasks(doneTasks)
                .build();
    }

    @Override
    public GetAllCreatedEpicsByUser getAllCreatedEpicsByUser(String userName) {
        List<Epic> epicList = taskDao.getEpicByUsername(userName);

        List<Epic> newEpics = new ArrayList<>();
        List<Epic> inProgressEpics = new ArrayList<>();
        List<Epic> doneEpics = new ArrayList<>();


        for (Epic epic : epicList) {
            Status status = getEpicStatus(epic);

            if (status == Status.NEW) {
                newEpics.add(epic);
            } else if (status == Status.IN_PROGRESS) {
                inProgressEpics.add(epic);
            } else if (status == Status.DONE) {
                doneEpics.add(epic);
            }
        }

        return GetAllCreatedEpicsByUser.builder()
                .inProgressEpics(inProgressEpics)
                .newEpics(newEpics)
                .doneEpics(doneEpics)
                .build();
    }

    @Override
    public Status getEpicStatus(Epic epic) {

        int statusNew = 0;
        int statusInProgress = 0;
        int statusDone = 0;

        for (SubTask subTask : epic.getSubTasks()) {
            Status currentStatus = subTask.getStatus();
            if (currentStatus == Status.IN_PROGRESS) {
                statusInProgress++;
            } else if (currentStatus == Status.NEW) {
                statusNew++;
            } else if (currentStatus == Status.DONE) {
                statusDone++;
            }
        }

        Status status = Status.UNDEFINED;
        if (statusInProgress == 0 && statusDone == 0) {
            status = Status.NEW;
        } else if (statusNew == 0 && statusInProgress == 0) {
            status = Status.DONE;
        } else if (statusInProgress > 0 || statusDone > 0 && statusNew != 0){
            status = Status.IN_PROGRESS;
        }
        return status;
    }
}
