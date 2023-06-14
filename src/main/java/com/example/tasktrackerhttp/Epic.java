package com.example.tasktrackerhttp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Epic extends Task {
    private final List<SubTask> subTasks = new ArrayList<>();
    public void addSubTask (SubTask subTask) {
        subTasks.add(subTask);
    }

    public Status getStatus () {
        AtomicInteger countStatusNew = new AtomicInteger();
        AtomicInteger countStatusInProgress = new AtomicInteger();
        AtomicInteger countStatusDone = new AtomicInteger();
        subTasks.stream()
                .map(Task::getStatus)
                .forEach(status -> {
                    if (status == Status.NEW) {
                        countStatusNew.getAndIncrement();
                    } else if (status == Status.IN_PROGRESS) {
                        countStatusInProgress.getAndIncrement();
                    } else if (status == Status.DONE) {
                        countStatusDone.getAndIncrement();
                    }
                });
        return calculateStatus(countStatusNew.get(), countStatusInProgress.get(), countStatusDone.get());
    }
    //    public Status getStatus () {
//        List<Status> subTasksStatus = new ArrayList<>();
//        subTasks.forEach(subTask -> subTasksStatus.add(subTask.getStatus()));
//        int countStatusNew = 0;
//        int countStatusInProgress = 0;
//        int countStatusDone = 0;
//        for (int i = 0; i < subTasksStatus.size(); i++) {
//            if (subTasksStatus.get(i) == Status.NEW) {
//                countStatusNew++;
//            }
//            if (subTasksStatus.get(i) == Status.IN_PROGRESS) {
//                countStatusInProgress++;
//            }
//            if (subTasksStatus.get(i) == Status.DONE) {
//                countStatusDone++;
//            }
//        }
//        return calculateStatus(countStatusNew, countStatusInProgress, countStatusDone);
//    }
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

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void removeSubTaskById (long id) {
        subTasks.removeIf(subTask -> subTask.getId() == id);
    }

    @Override
    public String toString() {
        return "Epic{" + System.lineSeparator() +
                "name=" + getName() + System.lineSeparator() +
                "id=" + getId() + System.lineSeparator() +
                "description=" + getDescription() + System.lineSeparator() +
                "status=" + getStatus() + System.lineSeparator() +
                "subTasks=" + subTasks + System.lineSeparator() +
                '}';
    }

}


