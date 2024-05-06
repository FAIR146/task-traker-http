package com.example.tasktrackerhttp.controller.ui;

import com.example.tasktrackerhttp.controller.core.ManagerControllerImpl;
import com.example.tasktrackerhttp.dao.TaskDao;
import com.example.tasktrackerhttp.dto.*;
import com.example.tasktrackerhttp.service.GetAllCreatedEpicsByUser;
import com.example.tasktrackerhttp.service.GetAllCreatedTasksByUser;
import com.example.tasktrackerhttp.service.Manager;
import com.example.tasktrackerhttp.service.ManagerImpl;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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
    public String updTask (Model model, @RequestParam long id) {
        Task task = manager.getTaskById(id);
        model.addAttribute("task", task);
        return "updTask";
    }
    @GetMapping("/updEpic")
    public String updEpic (Model model, @RequestParam long id) {
        Epic epic = manager.getEpicById(id);
        model.addAttribute("epic", epic);
        return "updEpic";
    }

    @GetMapping("/updateTaskRequest")
    public String updateTask(@RequestParam String name, @RequestParam String description, @RequestParam Status status, @RequestParam long id) {
        manager.updateTask(id, name, description, status);
        return "redirect:/tasks";
    }
    @GetMapping("/updateEpicRequest")
    public String updateEpic (@RequestParam String name, @RequestParam String description, @RequestParam long id){
        manager.updateEpic(id, name, description);
        return "redirect:/epics";
    }


    @GetMapping("/createSubTask")
    public String createSubTask (Model model, @RequestParam long id) {
        Epic epic = manager.getEpicById(id);
        model.addAttribute("epic", epic);
        return "createSubTask";
    }
    @PostMapping("/putSubTask")
    public String putSubTask(@RequestParam long id, @RequestParam String name, @RequestParam String description, @RequestParam Status status) {
        manager.addSubTask(id, name, description, status);
        return "redirect:/epics";
    }

    @GetMapping("/putTask")
    public String putTask (@RequestParam String name, @RequestParam String description, @RequestParam Status status, HttpSession session) {
        String login = (String) session.getAttribute("login");
        manager.addTask(name, description, status, login);
        return "redirect:/tasks";
    }
    @GetMapping("/putEpic")
    public String putEpic (@RequestParam String name, @RequestParam String description, HttpSession session) {
        String login = (String) session.getAttribute("login");
        manager.addEpic(name, description, login);
        return "redirect:/epics";
    }
    @GetMapping("/deleteEpicById")
    public String deleteEpicById (@RequestParam long id) {
        manager.removeEpicById(id);
        return "redirect:/epics";
    }
    @GetMapping("/deleteTaskById")
    public String deleteTaskById (@RequestParam long id) {
        manager.removeTaskById(id);
        return "redirect:/tasks";
    }
    @GetMapping("/deleteSubTaskById")
    public String deleteSubTaskById(@RequestParam long subtaskId) {
        manager.removeSubTaskById(subtaskId);
        return "redirect:/epics";
    }


    @GetMapping("/epics")
    public String drawEpics (Model model, HttpSession httpSession) {
        String login = (String) httpSession.getAttribute("login");
        GetAllCreatedEpicsByUser getAllCreatedEpicsByUser = manager.getAllCreatedEpicsByUser(login);

        log.info("Список epic {}",getAllCreatedEpicsByUser);

        model.addAttribute("epicsNew", getAllCreatedEpicsByUser.getNewEpics());
        model.addAttribute("epicsInProgress", getAllCreatedEpicsByUser.getInProgressEpics());
        model.addAttribute("epicsDone", getAllCreatedEpicsByUser.getDoneEpics());
        return "epicsBoard";
    }

    @GetMapping("/tasks")
    public String drawTasks(Model model, HttpSession httpSession) {
        String login = (String) httpSession.getAttribute("login");
        GetAllCreatedTasksByUser getAllCreatedTasksByUser = manager.getAllCreatedTasksByUser(login);;

        log.info("Список task {}", getAllCreatedTasksByUser);


        model.addAttribute("tasksNew", getAllCreatedTasksByUser.getNewTasks());
        model.addAttribute("tasksInProgress", getAllCreatedTasksByUser.getInProgressTasks());
        model.addAttribute("tasksDone", getAllCreatedTasksByUser.getDoneTasks());
        return "board";
    }
}
