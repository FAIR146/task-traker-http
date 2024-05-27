package com.example.tasktrackerhttp.controller.core;

import com.example.tasktrackerhttp.controller.core.put.PutEpicRequest;
import com.example.tasktrackerhttp.controller.core.put.PutSubTaskRequest;
import com.example.tasktrackerhttp.controller.core.put.PutTaskRequest;
import com.example.tasktrackerhttp.controller.core.put.UpdateEpicRequest;
import com.example.tasktrackerhttp.controller.core.put.UpdateSubTaskRequest;
import com.example.tasktrackerhttp.controller.core.put.UpdateTaskRequest;
import com.example.tasktrackerhttp.controller.core.response.GetEpicResponse;
import com.example.tasktrackerhttp.controller.core.response.GetSubTaskResponse;
import com.example.tasktrackerhttp.controller.core.response.GetTaskResponse;
import com.example.tasktrackerhttp.controller.core.response.PutEpicResponse;
import com.example.tasktrackerhttp.controller.core.response.PutSubTaskResponse;
import com.example.tasktrackerhttp.controller.core.response.PutTaskResponse;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.service.dto.GetAllCreatedEpicsByUser;
import com.example.tasktrackerhttp.service.dto.GetAllCreatedTasksByUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ManagerController {
    @PutMapping("/putTask")
    PutTaskResponse putTask (@Valid @RequestBody PutTaskRequest putTaskRequest, @AuthenticationPrincipal UserDetails userDetails);

    @PutMapping("/putEpic")
    PutEpicResponse putEpic (@Valid @RequestBody PutEpicRequest putEpicRequest, @AuthenticationPrincipal UserDetails userDetails);

    @PutMapping("/putSubTask")
    PutSubTaskResponse addSubTask (@Valid @RequestBody PutSubTaskRequest putSubTaskRequest);

    @DeleteMapping("/deleteEpicById")
    void deleteEpicById (@Valid @RequestParam long id);

    @DeleteMapping("/deleteTaskById")
    void deleteTaskById (@Valid @RequestParam long id);

    @DeleteMapping("/deleteSubTaskById")
    void deleteSubTaskById (@Valid @RequestParam long id);

    @GetMapping("/getEpicById")
    ResponseEntity<GetEpicResponse> getEpicById (@Valid @RequestParam long id);

    @GetMapping("/getTaskById")
    ResponseEntity<GetTaskResponse> getTaskById (@Valid @RequestParam long id);

    @GetMapping("/getSubTaskById")
    ResponseEntity<GetSubTaskResponse> getSubTaskById (@Valid @RequestParam long id);

    @PatchMapping("/updateEpic")
    void updateEpic (@Valid @RequestBody UpdateEpicRequest updateEpicRequest);

    @PatchMapping("/updateTask")
    void updateTask (@Valid @RequestBody UpdateTaskRequest updateTaskRequest);

    @PatchMapping ("/updateSubTask")
    void updateSubTask (@Valid @RequestBody UpdateSubTaskRequest updateSubTaskRequest);

    @GetMapping("/getAllCreatedTasksByUser")
    ResponseEntity<GetAllCreatedTasksByUser> getAllCreatedTasksByUser (@Valid @RequestParam String name);

    @GetMapping("/getAllCreatedEpicsByUser")
    ResponseEntity<GetAllCreatedEpicsByUser> getallCreatedEpicsByUser (@Valid @RequestParam String name);

    @GetMapping("/updateSubtaskStatus")
    void updateSubtaskStatus(@RequestParam("subtaskId") long id, @RequestParam Status status);
}
