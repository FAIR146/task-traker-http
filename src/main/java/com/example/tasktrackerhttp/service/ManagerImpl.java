package com.example.tasktrackerhttp.service;
import com.example.tasktrackerhttp.dao.TaskDao;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Task> inProgressTasks = taskDao.getTaskByStatusAndUserName(Status.IN_PROGRESS, userName);
        List<Task> newTasks = taskDao.getTaskByStatusAndUserName(Status.NEW, userName);
        List<Task> doneTasks = taskDao.getTaskByStatusAndUserName(Status.DONE, userName);

        return GetAllCreatedTasksByUser.builder()
                .newTasks(newTasks)
                .inProgressTasks(inProgressTasks)
                .doneTasks(doneTasks)
                .build();
    }

    public GetAllCreatedTasksByUser getAllCreatedEpicsByUser(String userName) { // TODO заменить return type
        List<Epic> userEpics = taskDao.getEpicByUsername(userName);
        //TODO разделить эпики по статусам на разные списки

        return null;
    }

    private Status getEpicStatus (List<SubTask> list) {

        int statusNew = 0;
        int statusInProgress = 0;
        int statusDone = 0;

        for (int i = 0; i < list.size(); i++) {
            Status currentStatus = list.get(i).getStatus();
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
        } else if (statusDone > 0){
            status = Status.IN_PROGRESS;
        } else if (statusNew == 0 && statusInProgress == 0) {
            status = Status.DONE;
        }
        return status;
    }

//    public Status getEpicStatus () {
//        List<Status> subTasksStatus = new ArrayList<>();
//        subTasks.forEach(subTask -> subTasksStatus.add(subTask.getStatus()));
//        int countStatusNew = 0;
//        int countStatusInProgress = 0;
//        int countStatusDone = 0;
//        for (Status tasksStatus : subTasksStatus) {
//            if (tasksStatus == Status.NEW) {
//                countStatusNew++;
//            }
//            if (tasksStatus == Status.IN_PROGRESS) {
//                countStatusInProgress++;
//            }
//            if (tasksStatus == Status.DONE) {
//                countStatusDone++;
//            }
//        }
//        return calculateStatus(countStatusNew, countStatusInProgress, countStatusDone);
//    }
//
//    private Status calculateStatus (int statusNew, int statusInProgress, int statusDone) {
//        if (statusInProgress > 0) {
//            return Status.IN_PROGRESS;
//        }
//        if (statusInProgress == 0 && statusDone == 0) {
//            return Status.NEW;
//        }
//        if (statusInProgress == 0 && statusNew == 0) {
//            return Status.DONE;
//        }
//        return Status.IN_PROGRESS;
//    }
}

