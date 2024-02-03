package com.example.tasktrackerhttp.controller.ui;

import com.example.tasktrackerhttp.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class BoardController {

    @GetMapping("/tasks")
    public String drawTasks(Model model) {
        List<Epic> epicList = generateDummyEpics();
        log.info("Список dummy {}", epicList);

        model.addAttribute("epics", generateDummyEpics());
        return "board";
    }
//    @GetMapping("/tasks")
//    public String drawUserName(Model model) {
//        model.addAttribute("name", userGetName());
//         return null;
//    }

//    private String userGetName () {
//        return null; /*user.getName(); */
//    }
    private List<Epic> generateDummyEpics() {
        return new ArrayList<>() {{
            add(createEpic1());
            add(createEpic2());
        }};
    }

    private Epic createEpic1() {
        Epic epic = new Epic();
        epic.setDescription("Описание эпика");
        epic.setId(123);
        epic.setName("Name of epic");

        SubTask subTask = new SubTask() {{
            setStatus(Status.NEW);
            setId(1);
            setName("1 subtas name");
            setDescription("1 subtask description");
        }};

        SubTask subTask2 = new SubTask() {{
            setStatus(Status.DONE);
            setId(2);
            setName("2 subtas name");
            setDescription("2 subtask description");
        }};

        epic.getSubTasks().add(subTask);
        epic.getSubTasks().add(subTask2);
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
            setName("3 subtas name");
            setDescription("3 subtask description");
        }};

        SubTask subTask2 = new SubTask() {{
            setStatus(Status.NEW);
            setId(4);
            setName("4 subtas name");
            setDescription("4 subtask description");
        }};

        epic.getSubTasks().add(subTask);
        epic.getSubTasks().add(subTask2);
        return epic;
    }

}
