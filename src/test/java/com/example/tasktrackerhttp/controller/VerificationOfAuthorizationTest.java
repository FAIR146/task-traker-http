package com.example.tasktrackerhttp.controller;


import com.example.tasktrackerhttp.exception.UserVerificationException;
import com.example.tasktrackerhttp.dao.TaskDao;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.Task;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class VerificationOfAuthorizationTest extends BaseAuthenticatedControllerTest{
    @Autowired
    private TaskDao taskDao;

    @Test
    public void putTask () throws Exception {
       Task task = new Task();
       task.setUserName("test_user2");
       task.setName("проверка");
       task.setDescription("124");
       task.setStatus(Status.NEW);
       long id = taskDao.addTask(task);

       try {
           mockMvc.perform(
                   get("/deleteTaskById")
                           .param("id",String.valueOf(id))
           );
       } catch (ServletException exception) {
           if (!(exception.getCause() instanceof UserVerificationException)) {
               throw exception;
           }
       }
    }
}
