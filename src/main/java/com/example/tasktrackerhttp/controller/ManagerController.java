package com.example.tasktrackerhttp.controller;

import com.example.tasktrackerhttp.controller.response.AddTaskResponse;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.service.Manager;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import com.example.tasktrackerhttp.controller.request.AddSubTaskRequest;
import com.example.tasktrackerhttp.controller.response.AddEpicResponse;
import com.example.tasktrackerhttp.controller.response.AddSubTaskResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManagerController {
    private final Manager manager;
    public ManagerController(Manager manager) {
        this.manager = manager;
    }

    @PutMapping("/putTask")
    public AddTaskResponse putTask (@RequestBody Task task) {
        AddTaskResponse addTaskResponse = new AddTaskResponse();
        long id = manager.addTask(task.getName(),task.getDescription(), task.getStatus());
        addTaskResponse.setId(id);
        return addTaskResponse;
    }
    @PutMapping("/putEpic")
    public AddEpicResponse putEpic (@RequestBody Epic epic) {
        AddEpicResponse addEpicResponse = new AddEpicResponse();
        long id = manager.addEpic(epic.getName(), epic.getDescription());
        addEpicResponse.setId(id);
        return addEpicResponse;
    }

    @PutMapping("/putSubTask")
    public AddSubTaskResponse addSubTask (@RequestBody AddSubTaskRequest addSubTaskRequest) {
        AddSubTaskResponse addSubTaskResponse = new AddSubTaskResponse();
        Epic epic = new Epic();
        long id = manager.addSubTask(addSubTaskResponse.getId(), addSubTaskRequest.getName(), addSubTaskRequest.getDescription(), addSubTaskRequest.getStatus());
        addSubTaskResponse.setId(id);
        return addSubTaskResponse;
    }
    @DeleteMapping("/deleteEpicById")
    public void deleteEpicById (@RequestParam long id) {
        manager.removeEpicById(id);
    }
    @DeleteMapping("/deleteTaskById")
    public void deleteTaskById (@RequestParam long id) {
        manager.removeTaskById(id);

    }
    @DeleteMapping("/deleteSubTaskById")
    public void deleteSubTaskById (@RequestParam long id) {
        manager.removeSubTaskById(id);
    }
    @DeleteMapping("/deleteAllTasks")
    public void deleteAllTasks () {
        manager.removeAllTasks();
    }
    @DeleteMapping("/deleteAllEpics")
    public void deleteAllEpics () {
        manager.removeAllEpics();
    }
    @DeleteMapping("/deleteAllSubTasks")
    public void deleteAllSubTasks () {
        manager.removeAllSubTasks();
    }
    @GetMapping("/getEpicById")
    public Epic getEpicById (@RequestParam long id) {
        return manager.getEpicById(id);
    }
    @GetMapping("/getTaskById")
    public Task getTaskById (@RequestParam long id) {
        return manager.getTaskById(id);
    }
    @GetMapping("/getSubTaskById/{id}")
    public SubTask getSubTaskById (@PathVariable long id) {
        return manager.getSubTaskById(id);
    }
    @GetMapping("/getAllTasks")
    public List<Task> getAllTasks () {
        return manager.getAllTasks();
    }
    @GetMapping("/getAllEpics")
    public List<Epic> getAllEpics () {
        return manager.getAllEpics();
    }
    @GetMapping("/getAllSubTasks")
    public List<SubTask> getAllSubTasks () {
        return manager.getAllSubTasks();
    }

}
