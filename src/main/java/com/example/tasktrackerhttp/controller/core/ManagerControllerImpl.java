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
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.service.dto.GetAllCreatedEpicsByUser;
import com.example.tasktrackerhttp.service.dto.GetAllCreatedTasksByUser;
import com.example.tasktrackerhttp.service.Manager;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;

import jakarta.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ManagerControllerImpl implements ManagerController {
    private final Manager manager;
    public ManagerControllerImpl(Manager manager) {
        this.manager = manager;
    }

    @Override
    public PutTaskResponse putTask (PutTaskRequest putTaskRequest, @AuthenticationPrincipal UserDetails userDetails) {
        PutTaskResponse putTaskResponse = new PutTaskResponse();
        String login = userDetails.getUsername();
        long id = manager.addTask(putTaskRequest.getName(),putTaskRequest.getDescription(), putTaskRequest.getStatus(), login);
        putTaskResponse.setId(id);
        log.info("Добавлена таска {}", putTaskResponse);
        return putTaskResponse;
    }

    @Override
    public PutEpicResponse putEpic (PutEpicRequest putEpicRequest, @AuthenticationPrincipal UserDetails userDetails) {
        PutEpicResponse putEpicResponse = new PutEpicResponse();
        String login = userDetails.getUsername();
        long id = manager.addEpic(putEpicRequest.getName(), putEpicRequest.getDescription(), login);
        putEpicResponse.setId(id);
        log.info("Добавлен епик {}", putEpicResponse);
        return putEpicResponse;
    }

    @Override
    public PutSubTaskResponse addSubTask (PutSubTaskRequest putSubTaskRequest) {
        PutSubTaskResponse putSubTaskResponse = new PutSubTaskResponse();
        long id = manager.addSubTask(putSubTaskRequest.getEpicId(), putSubTaskRequest.getName(), putSubTaskRequest.getDescription(), putSubTaskRequest.getStatus());
        putSubTaskResponse.setId(id);
        log.info("Добавлена сабТаска {}", putSubTaskResponse);
        log.info("Добавлена сабтаска {}", putSubTaskRequest);
        return putSubTaskResponse;
    }

    public void updateSubtaskStatus(long id, Status status) {
        System.out.println();
        //manager.updateSubtaskStatus();  UPDATE SUBTASK SET STATUS = "~STATUS~" WHERE id = ~id~;
    }

    @Override
    public void deleteEpicById (long id) {
        manager.removeEpicById(id);
        log.info("Удален епик с id {}", id);
        log.info("Удален епик с id{}", id);
    }

    @Override
    public void deleteTaskById (long id) {
        manager.removeTaskById(id);
        log.info("Удалена таска с id {}", id);
        log.info("Удалена таска с id{}", id);

    }
    @Override
    public void deleteSubTaskById (long id) {
        manager.removeSubTaskById(id);

        log.info("Удалена сабТаска с id {}", id);
        log.info("Удалена сабтаска с id{}", id);

    }

    @Override
    public ResponseEntity<GetEpicResponse> getEpicById (@RequestParam long id) {
        Epic epic = manager.getEpicById(id);
        if (epic == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GetEpicResponse getEpicResponse = new GetEpicResponse();
        getEpicResponse.setId(epic.getId());
        getEpicResponse.setName(epic.getName());
        getEpicResponse.setDescription(epic.getDescription());
        getEpicResponse.setUserName(epic.getUserName());
        getEpicResponse.setSubTasks(epic.getSubTasks().stream().map(subTask -> {
            GetSubTaskResponse getSubTaskResponse = new GetSubTaskResponse();
            getSubTaskResponse.setStatus(subTask.getStatus());
            getSubTaskResponse.setDescription(subTask.getDescription());
            getSubTaskResponse.setId(subTask.getId());
            getSubTaskResponse.setName(subTask.getName());
            getSubTaskResponse.setEpicId(subTask.getEpicId());
            return getSubTaskResponse;
        }).toList());
        log.info("Получен епик {}", getEpicResponse);
        return ResponseEntity.ok(getEpicResponse);
    }

    @Override
    public ResponseEntity<GetTaskResponse> getTaskById (@RequestParam long id) {
        Task task = manager.getTaskById(id);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GetTaskResponse getTaskResponse = new GetTaskResponse();
        getTaskResponse.setName(task.getName());
        getTaskResponse.setDescription(task.getDescription());
        getTaskResponse.setStatus(task.getStatus());
        getTaskResponse.setId(task.getId());
        getTaskResponse.setUserName(task.getUserName());
        log.info("Получена таска {}", getTaskResponse);
        return ResponseEntity.ok(getTaskResponse);
    }

    @Override
    public ResponseEntity<GetSubTaskResponse> getSubTaskById (@RequestParam long id) {
        SubTask subTask = manager.getSubTaskById(id);
        if (subTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GetSubTaskResponse getSubTaskResponse = new GetSubTaskResponse();
        getSubTaskResponse.setId(subTask.getId());
        getSubTaskResponse.setEpicId(subTask.getEpicId());
        getSubTaskResponse.setDescription(subTask.getDescription());
        getSubTaskResponse.setName(subTask.getName());
        getSubTaskResponse.setStatus(subTask.getStatus());
        log.info("Получена сабТаска {}", getSubTaskResponse);
        return ResponseEntity.ok(getSubTaskResponse);
    }

    @Override
    public void updateEpic (UpdateEpicRequest updateEpicRequest) {
        manager.updateEpic(updateEpicRequest.getId(), updateEpicRequest.getName(), updateEpicRequest.getDescription());
        log.info("Обновлён епик {}", updateEpicRequest);
        log.info("Обновлен епик {}", updateEpicRequest);
    }

    @Override
    public void updateTask (UpdateTaskRequest updateTaskRequest) {
        manager.updateTask(updateTaskRequest.getId(), updateTaskRequest.getName(), updateTaskRequest.getDescription(), updateTaskRequest.getStatus());
        log.info("Обновлена таска {}", updateTaskRequest);
    }

    @Override
    public void updateSubTask (UpdateSubTaskRequest updateSubTaskRequest) {
        manager.updateSubTask(updateSubTaskRequest.getId(), updateSubTaskRequest.getName(), updateSubTaskRequest.getDescription(), updateSubTaskRequest.getStatus());
        log.info("Обновлена сабТаска {}", updateSubTaskRequest);
    }

    @Override
    public ResponseEntity<GetAllCreatedTasksByUser> getAllCreatedTasksByUser (String name) {
        GetAllCreatedTasksByUser getAllCreatedTasksByUser = manager.getAllCreatedTasksByUser(name);
        if (getAllCreatedTasksByUser == null) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Получены все таски юзера {}", getAllCreatedTasksByUser);
        return  ResponseEntity.ok(getAllCreatedTasksByUser);
    }

    @Override
    public ResponseEntity<GetAllCreatedEpicsByUser> getallCreatedEpicsByUser (String name) {
        GetAllCreatedEpicsByUser getAllCreatedEpicsByUser = manager.getAllCreatedEpicsByUser(name);
        if(getAllCreatedEpicsByUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Получены все епики юзера {}", getAllCreatedEpicsByUser);
        return ResponseEntity.ok(getAllCreatedEpicsByUser);
    }
}
