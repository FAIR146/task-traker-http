package com.example.tasktrackerhttp.controller;

import com.example.tasktrackerhttp.controller.request.put.*;
import com.example.tasktrackerhttp.controller.response.*;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.service.Manager;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManagerController {
    private final Manager manager;
    public ManagerController(Manager manager) {
        this.manager = manager;
    }

    @PutMapping("/putTask")
    public PutTaskResponse putTask (@RequestBody PutTaskRequest putTaskRequest) {
        PutTaskResponse putTaskResponse = new PutTaskResponse();
        long id = manager.addTask(putTaskRequest.getName(),putTaskRequest.getDescription(), putTaskRequest.getStatus());
        putTaskResponse.setId(id);
        return putTaskResponse;
    }
    @PutMapping("/putEpic")
    public PutEpicResponse putEpic (@RequestBody PutEpicRequest putEpicRequest) {
        PutEpicResponse putEpicResponse = new PutEpicResponse();
        long id = manager.addEpic(putEpicRequest.getName(), putEpicRequest.getDescription());
        putEpicResponse.setId(id);
        return putEpicResponse;
    }

    @PutMapping("/putSubTask")
    public PutSubTaskResponse addSubTask (@RequestBody PutSubTaskRequest putSubTaskRequest) {
        PutSubTaskResponse putSubTaskResponse = new PutSubTaskResponse();
        Epic epic = new Epic();
        long id = manager.addSubTask(putSubTaskResponse.getId(), putSubTaskRequest.getName(), putSubTaskRequest.getDescription(), putSubTaskRequest.getStatus());
        putSubTaskResponse.setId(id);
        return putSubTaskResponse;
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
    public ResponseEntity<Task> getTaskById (@RequestParam long id) {
        Task task = manager.getTaskById(id);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(task);
    }
    @GetMapping("/getSubTaskById")
    public SubTask getSubTaskById (@RequestParam long id) {
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
    @PatchMapping("/updateEpic")
    public void updateEpic (@RequestBody UpdateEpicRequest updateEpicRequest) {
        manager.updateEpic(updateEpicRequest.getId(), updateEpicRequest.getName(), updateEpicRequest.getDescription());
    }
    @PatchMapping("/updateTask")
    public void updateTask (@RequestBody UpdateTaskRequest updateTaskRequest) {
        manager.updateTask(updateTaskRequest.getId(), updateTaskRequest.getName(), updateTaskRequest.getDescription(), updateTaskRequest.getStatus());
    }
    @PatchMapping ("/updateSubTask")
    public void  updateSubTask (@RequestBody UpdateSubTaskRequest updateSubTaskRequest) {
        manager.updateSubTask(updateSubTaskRequest.getId(), updateSubTaskRequest.getName(), updateSubTaskRequest.getDescription(), updateSubTaskRequest.getStatus());
    }

}