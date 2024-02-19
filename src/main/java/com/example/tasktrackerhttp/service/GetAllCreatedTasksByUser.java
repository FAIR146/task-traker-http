package com.example.tasktrackerhttp.service;

import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Task;

import java.util.List;

public class GetAllCreatedTasksByUser {
    private List<Task> inProgressTasks;
    private List<Task> newTasks;
    private List<Task> doneTasks;
    private List<Epic> inProgressEpic;
    private List<Epic> newEpic;
    private List<Epic> doneEpic;

    public List<Task> getInProgressTasks() {
        return  inProgressTasks;
    }

    public void setInProgressTasks(List<Task> inProgressTasks) {
        this.inProgressTasks = inProgressTasks;
    }

    public List<Task> getNewTasks() {
        return newTasks;
    }

    public void setNewTasks(List<Task> newTasks) {
        this.newTasks = newTasks;
    }

    public List<Task> getDoneTasks() {
        return doneTasks;
    }

    public void setDoneTasks(List<Task> doneTasks) {
        this.doneTasks = doneTasks;
    }

    public List<Epic> getInProgressEpic() {
        return inProgressEpic;
    }

    public void setInProgressEpic(List<Epic> inProgressEpic) {
        this.inProgressEpic = inProgressEpic;
    }

    public List<Epic> getNewEpic() {
        return newEpic;
    }

    public void setNewEpic(List<Epic> newEpic) {
        this.newEpic = newEpic;
    }

    public List<Epic> getDoneEpic() {
        return doneEpic;
    }

    public void setDoneEpic(List<Epic> doneEpic) {
        this.doneEpic = doneEpic;
    }
}
