package com.example.tasktrackerhttp.controller.core;

import com.example.tasktrackerhttp.controller.core.put.PutEpicRequest;
import com.example.tasktrackerhttp.controller.core.put.PutSubTaskRequest;
import com.example.tasktrackerhttp.controller.core.put.PutTaskRequest;
import com.example.tasktrackerhttp.controller.core.put.UpdateEpicRequest;
import com.example.tasktrackerhttp.controller.core.put.UpdateSubTaskRequest;
import com.example.tasktrackerhttp.controller.core.put.UpdateTaskRequest;
import com.example.tasktrackerhttp.controller.core.response.PutEpicResponse;
import com.example.tasktrackerhttp.controller.core.response.PutSubTaskResponse;
import com.example.tasktrackerhttp.controller.core.response.PutTaskResponse;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import com.example.tasktrackerhttp.service.GetAllCreatedEpicsByUser;
import com.example.tasktrackerhttp.service.GetAllCreatedTasksByUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ManagerController {
    @PutMapping("/putTask")
    PutTaskResponse putTask (@RequestBody PutTaskRequest putTaskRequest, HttpSession session);

    @PutMapping("/putEpic")
    PutEpicResponse putEpic (@RequestBody PutEpicRequest putEpicRequest, HttpSession session);

    @PutMapping("/putSubTask")
    PutSubTaskResponse addSubTask (@RequestBody PutSubTaskRequest putSubTaskRequest);

    @DeleteMapping("/deleteEpicById")
    void deleteEpicById (@RequestParam long id);

    @DeleteMapping("/deleteTaskById")
    void deleteTaskById (@RequestParam long id);

    @DeleteMapping("/deleteSubTaskById")
    void deleteSubTaskById (@RequestParam long id);

    @GetMapping("/getEpicById")
    ResponseEntity<Epic> getEpicById (@RequestParam long id);

    @GetMapping("/getTaskById")
    ResponseEntity<Task> getTaskById (@RequestParam long id);

    @GetMapping("/getSubTaskById")
    ResponseEntity<SubTask> getSubTaskById (@RequestParam long id);

    @PatchMapping("/updateEpic")
    void updateEpic (@RequestBody UpdateEpicRequest updateEpicRequest);

    @PatchMapping("/updateTask")
    void updateTask (@RequestBody UpdateTaskRequest updateTaskRequest);

    @PatchMapping ("/updateSubTask")
    void updateSubTask (@RequestBody UpdateSubTaskRequest updateSubTaskRequest);

    @GetMapping("/getAllCreatedTasksByUser")
    ResponseEntity<GetAllCreatedTasksByUser> getAllCreatedTasksByUser (@RequestParam String name);

    @GetMapping("/getAllCreatedEpicsByUser")
    ResponseEntity<GetAllCreatedEpicsByUser> getallCreatedEpicsByUser (@RequestParam String name);

}
