package com.example.tasktrackerhttp.controller.core;

import com.example.tasktrackerhttp.controller.core.put.*;
import com.example.tasktrackerhttp.controller.core.response.*;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.service.GetAllCreatedEpicsByUser;
import com.example.tasktrackerhttp.service.GetAllCreatedTasksByUser;
import com.example.tasktrackerhttp.service.Manager;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerController {
    private final Manager manager;
    public ManagerController(Manager manager) {
        this.manager = manager;
    }

    @PutMapping("/putTask")
    public PutTaskResponse putTask (@RequestBody PutTaskRequest putTaskRequest, HttpSession session) {
        PutTaskResponse putTaskResponse = new PutTaskResponse();
        String login = (String) session.getAttribute("login");
        long id = manager.addTask(putTaskRequest.getName(),putTaskRequest.getDescription(), putTaskRequest.getStatus(), login);
        putTaskResponse.setId(id);
        return putTaskResponse;
    }
    @PutMapping("/putEpic")
    public PutEpicResponse putEpic (@RequestBody PutEpicRequest putEpicRequest, HttpSession session) {
        PutEpicResponse putEpicResponse = new PutEpicResponse();
        String login = (String) session.getAttribute("login");
        long id = manager.addEpic(putEpicRequest.getName(), putEpicRequest.getDescription(), login);
        putEpicResponse.setId(id);
        return putEpicResponse;
    }

    @PutMapping("/putSubTask")
    public PutSubTaskResponse addSubTask (@RequestBody PutSubTaskRequest putSubTaskRequest) {
        PutSubTaskResponse putSubTaskResponse = new PutSubTaskResponse();
        long id = manager.addSubTask(putSubTaskRequest.getEpicId(), putSubTaskRequest.getName(), putSubTaskRequest.getDescription(), putSubTaskRequest.getStatus());
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

    @GetMapping("/getEpicById")
    public ResponseEntity<Epic> getEpicById (@RequestParam long id) {
        Epic epic = manager.getEpicById(id);
        if (epic == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(epic);
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
    public ResponseEntity<SubTask> getSubTaskById (@RequestParam long id) {
        SubTask subTask = manager.getSubTaskById(id);
        if (subTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(subTask);
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
    @GetMapping("/getAllCreatedTasksByUser")
    public  ResponseEntity<GetAllCreatedTasksByUser> getAllCreatedTasksByUser (@RequestParam String name) {
        GetAllCreatedTasksByUser getAllCreatedTasksByUser = manager.getAllCreatedTasksByUser(name);
        if (getAllCreatedTasksByUser == null) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  ResponseEntity.ok(getAllCreatedTasksByUser);
    }
    @GetMapping("/getAllCreatedEpicsByUser")
    public  ResponseEntity<GetAllCreatedEpicsByUser> getallCreatedEpicsByUser (@RequestParam String name) {
        GetAllCreatedEpicsByUser getAllCreatedEpicsByUser = manager.getAllCreatedEpicsByUser(name);
        if(getAllCreatedEpicsByUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(getAllCreatedEpicsByUser);
    }

}
