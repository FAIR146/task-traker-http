package com.example.tasktrackerhttp.controller.core.put;
import jakarta.servlet.http.HttpSession;
public class AbstractPutRequest {
    private String name;
    private String description;
    private String userName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName(HttpSession session) {
        return (String) session.getAttribute("login");
    }

    public void setUserName(HttpSession session) {
        this.userName = (String) session.getAttribute("login");
    }
}
