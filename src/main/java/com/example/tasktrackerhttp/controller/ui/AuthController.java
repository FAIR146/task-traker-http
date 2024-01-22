package com.example.tasktrackerhttp.controller.ui;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class AuthController {
    @GetMapping("login")
    public String loginPage(HttpSession session) {
        String login = (String) session.getAttribute("login");
        if (login != null) {
            log.info("{} user was authentificated", login);
            return "redirect:/tasks";
        } else {
            log.info("anonimous user");
        }
        return "logIn";
    }

    @PostMapping("/startAuth")
    public String startAuth(@RequestParam String login, HttpSession session) {
        session.setAttribute("login", login);
        return "redirect:/tasks";
    }
}
