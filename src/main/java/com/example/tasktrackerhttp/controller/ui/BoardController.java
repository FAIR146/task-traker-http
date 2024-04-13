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

    private List<Task> generateDummyTasks() {
        return new ArrayList<>(){{
            add(createTask1());
            add(createTask2());
        }};
    }

    private Task createTask1() {
        Task task = new Task();
        task.setName("gogo");
        task.setDescription("desc");
        task.setId(42);
        task.setStatus(Status.NEW);
        return task;
    }
    private Task createTask2() {
        Task task = new Task();
        task.setName("ghd");
        task.setDescription("descriptin");
        task.setId(44);
        task.setStatus(Status.NEW);
        return task;
    }
    private Epic createEpic1() {
        Epic epic = new Epic();
        epic.setDescription("Описание эпика");
        epic.setId(123);
        epic.setName("Name of epic");


        SubTask subTask = new SubTask() {{
            setStatus(Status.NEW);
            setId(1);
            setName("1 subtask name");
            setDescription("1 subtask description");
        }};

        SubTask subTask2 = new SubTask() {{
            setStatus(Status.DONE);
            setId(2);
            setName("2 subtask name");
            setDescription("2 subtask description");
        }};

        List<SubTask> subTasks = new ArrayList<>();
        subTasks.add(subTask2);
        subTasks.add(subTask);

        epic.setSubTasks(subTasks);

        return epic;
    }

    private Epic createEpic2() {
        Epic epic = new Epic();
        epic.setDescription("Описание эпика2");
        epic.setId(1233);
        epic.setName("Name of epic2");

        SubTask subTask = new SubTask() {{
            setStatus(Status.DONE);
            setId(3);
            setName("3 subtask name");
            setDescription("3 subtask description");
        }};

        SubTask subTask2 = new SubTask() {{
            setStatus(Status.NEW);
            setId(4);
            setName("4 subtask name");
            setDescription("4 subtask description");
        }};

        List<SubTask> subTasks = new ArrayList<>();
        subTasks.add(subTask2);
        subTasks.add(subTask);

        epic.setSubTasks(subTasks);
        return epic;
    }
//    @GetMapping("/tasks")
//    public String drawUserName(Model model) {
//        model.addAttribute("name", userGetName());
//         return null;
//    }

//    private String userGetName () {
//        return null; /*user.getName(); */
//    }
}
