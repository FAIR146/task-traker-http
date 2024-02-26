package com.example.tasktrackerhttp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Epic extends Task {
    private List<SubTask> subTasks = new ArrayList<>();

    public List<SubTask> getSubTasks() {
        return subTasks;
    }
    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public Status getEpicStatus () {
        List<Status> subTasksStatus = new ArrayList<>();
        subTasks.forEach(subTask -> subTasksStatus.add(subTask.getStatus()));
        int countStatusNew = 0;
        int countStatusInProgress = 0;
        int countStatusDone = 0;
        for (Status tasksStatus : subTasksStatus) {
            if (tasksStatus == Status.NEW) {
                countStatusNew++;
            }
            if (tasksStatus == Status.IN_PROGRESS) {
                countStatusInProgress++;
            }
            if (tasksStatus == Status.DONE) {
                countStatusDone++;
            }
        }
        return calculateStatus(countStatusNew, countStatusInProgress, countStatusDone);
    }

    private Status calculateStatus (int statusNew, int statusInProgress, int statusDone) {
        if (statusInProgress > 0) {
            return Status.IN_PROGRESS;
        }
        if (statusInProgress == 0 && statusDone == 0) {
            return Status.NEW;
        }
        if (statusInProgress == 0 && statusNew == 0) {
            return Status.DONE;
        }
        return Status.IN_PROGRESS;
    }
}


