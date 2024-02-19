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


    public long addTask (String name, String description, Status status, String userName) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        task.setUserName(userName);
        return taskDao.addTask(task);
    }

    public long addEpic(String name, String description, String userName) {
        Epic epic = new Epic();
        epic.setName(name);
        epic.setDescription(description);
        epic.setUserName(userName);
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
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        taskDao.updateTask(task);
    }
    public void updateEpic (long id, String name, String description) {
        Epic epic = new Epic();
        List<SubTask> subTasks = taskDao.getEpicById(id).getSubTasks();
        epic.setName(name);
        epic.setDescription(description);
        epic.setId(id);
        taskDao.updateEpic(epic);
    }
    public void updateSubTask (long id,  String name, String description, Status status) { //long epicId
        SubTask subTask = taskDao.getSubTaskById(id);
        subTask.setId(id);
//       subTask.setEpicId(epicId);
        subTask.setName(name);
        subTask.setDescription(description);
        subTask.setStatus(status);
        taskDao.updateSubTask(subTask);
    }

    @Override
    public GetAllCreatedTasksByUser getAllCreatedTasksByUser(String userName) {
        GetAllCreatedTasksByUser allCreatedTasksByUser = new GetAllCreatedTasksByUser();

        List<Task> inProgressTasks = taskDao.getInProgressTaskByUserName(userName);
        List<Task> newTasks = taskDao.getNewTaskByUserName(userName);
        List<Task> doneTasks = taskDao.getDoneTaskByUserName(userName);

        allCreatedTasksByUser.setInProgressTasks(inProgressTasks);
        allCreatedTasksByUser.setNewTasks(newTasks);
        allCreatedTasksByUser.setDoneTasks(doneTasks);
        return allCreatedTasksByUser;
    }
}

