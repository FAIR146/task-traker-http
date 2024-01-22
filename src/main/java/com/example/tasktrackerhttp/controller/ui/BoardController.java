package com.example.tasktrackerhttp.controller.ui;

import com.example.tasktrackerhttp.dto.Task;
import com.example.tasktrackerhttp.service.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {

    @GetMapping("/tasks")
    public String drawTasks(Model model) {
        model.addAttribute("tasks", generateDummyTask());
        return "board";
    }

    private List<Task> generateDummyTask() {
        return new ArrayList<>() {{
            Task task0 = new Task();
            task0.setName("task0name");
            Task task1 = new Task();
            task1.setName("task1name");
            Task task2 = new Task();
            task2.setName("task2name");

            add(task0);
            add(task1);
            add(task2);
        }};
    }

}
