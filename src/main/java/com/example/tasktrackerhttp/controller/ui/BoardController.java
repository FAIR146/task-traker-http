package com.example.tasktrackerhttp.controller.ui;

import com.example.tasktrackerhttp.exception.UserVerificationException;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import com.example.tasktrackerhttp.service.dto.GetAllCreatedEpicsByUser;
import com.example.tasktrackerhttp.service.dto.GetAllCreatedTasksByUser;
import com.example.tasktrackerhttp.service.Manager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class BoardController {
    private final Manager manager;

    public BoardController (Manager manager) {
        this.manager = manager;
    }

    @GetMapping("/board/test")
    public String drawTasks(Model model) {
        return "test";
    }

    @GetMapping("/createTask")
    public String createTask (Model model) {
        return "createTask";
    }

    @GetMapping("/createEpic")
    public String createEpic (Model model) {
        return "createEpic";
    }

    @GetMapping("/updTask")
    public String updTask (Model model, @RequestParam long id, @AuthenticationPrincipal UserDetails userDetails) {
        Task task = manager.getTaskById(id);
        verifyUser(task.getUserName(), userDetails);
        model.addAttribute("task", task);
        return "updTask";
    }

    @GetMapping("/updEpic")
    public String updEpic (Model model, @RequestParam long id, @AuthenticationPrincipal UserDetails userDetails) {
        Epic epic = manager.getEpicById(id);
        verifyUser(epic.getUserName(),userDetails);
        model.addAttribute("epic", epic);
        return "updEpic";
    }

    @GetMapping("/updateTaskRequest")
    public String updateTask(@RequestParam String name, @RequestParam String description, @RequestParam Status status, @RequestParam long id, @AuthenticationPrincipal UserDetails userDetails) {
        Task task = manager.getTaskById(id);
        verifyUser(task.getUserName(), userDetails);
        manager.updateTask(id, name, description, status);
        return "redirect:/tasks";
    }

    @GetMapping("/updateEpicRequest")
    public String updateEpic (@RequestParam String name, @RequestParam String description, @RequestParam long id, @AuthenticationPrincipal UserDetails userDetails){
        Epic epic = manager.getEpicById(id);
        verifyUser(epic.getUserName(), userDetails);
        manager.updateEpic(id, name, description);
        return "redirect:/epics";
    }

    @GetMapping("/createSubTask")
    public String createSubTask (Model model, @RequestParam long id, @AuthenticationPrincipal UserDetails userDetails) {
        Epic epic = manager.getEpicById(id);
        verifyUser(epic.getUserName(),userDetails);
        model.addAttribute("epic", epic);
        return "createSubTask";
    }

    @PostMapping("/putSubTask")
    public String putSubTask(@RequestParam long id, @RequestParam String name, @RequestParam String description, @RequestParam Status status, @AuthenticationPrincipal UserDetails userDetails) {
        Epic epic = manager.getEpicById(id);
        verifyUser(epic.getUserName(),userDetails);
        manager.addSubTask(id, name, description, status);
        return "redirect:/epics";
    }

    @GetMapping("/putTask")
    public String putTask (@RequestParam String name, @RequestParam String description, @RequestParam Status status, @AuthenticationPrincipal UserDetails userDetails) {
        String login = userDetails.getUsername();
        manager.addTask(name, description, status, login);
        return "redirect:/tasks";
    }

    @GetMapping("/putEpic")
    public String putEpic (@RequestParam String name, @RequestParam String description, @AuthenticationPrincipal UserDetails userDetails) {
        String login = userDetails.getUsername();
        manager.addEpic(name, description, login);
        return "redirect:/epics";
    }

    @GetMapping("/deleteEpicById")
    public String deleteEpicById (@RequestParam long id, @AuthenticationPrincipal UserDetails userDetails) {
        Epic epic = manager.getEpicById(id);
        verifyUser(epic.getUserName(),userDetails);
        manager.removeEpicById(id);
        return "redirect:/epics";
    }

    @GetMapping("/deleteTaskById")
    public String deleteTaskById (@RequestParam long id, @AuthenticationPrincipal UserDetails userDetails) {
        Task task = manager.getTaskById(id);
        verifyUser(task.getUserName(),userDetails);
        manager.removeTaskById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/deleteSubTaskById")
    public String deleteSubTaskById(@RequestParam long subtaskId, @AuthenticationPrincipal UserDetails userDetails) {
        SubTask subTask = manager.getSubTaskById(subtaskId);
        verifyUser(subTask.getUserName(),userDetails);
        manager.removeSubTaskById(subtaskId);
        return "redirect:/epics";
    }

    @GetMapping("/epics")
    public String drawEpics (Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String login = userDetails.getUsername();
        GetAllCreatedEpicsByUser getAllCreatedEpicsByUser = manager.getAllCreatedEpicsByUser(login);

        log.info("Список epic {}",getAllCreatedEpicsByUser);

        model.addAttribute("epicsNew", getAllCreatedEpicsByUser.getNewEpics());
        model.addAttribute("epicsInProgress", getAllCreatedEpicsByUser.getInProgressEpics());
        model.addAttribute("epicsDone", getAllCreatedEpicsByUser.getDoneEpics());
        return "epicsBoard";
    }

    @GetMapping("/tasks")
    public String drawTasks(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        String login = userDetails.getUsername();
        GetAllCreatedTasksByUser getAllCreatedTasksByUser = manager.getAllCreatedTasksByUser(login);;

        log.info("Список task {}", getAllCreatedTasksByUser);

        model.addAttribute("tasksNew", getAllCreatedTasksByUser.getNewTasks());
        model.addAttribute("tasksInProgress", getAllCreatedTasksByUser.getInProgressTasks());
        model.addAttribute("tasksDone", getAllCreatedTasksByUser.getDoneTasks());
        return "board";
    }

    private void verifyUser (String usernameObject, UserDetails userDetails) {
        if (!usernameObject.equals(userDetails.getUsername())) {
            throw new UserVerificationException("authorization-error");
        }
    }
}
