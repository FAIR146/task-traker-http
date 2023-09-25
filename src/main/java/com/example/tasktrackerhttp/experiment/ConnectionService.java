package com.example.tasktrackerhttp.experiment;

import com.example.tasktrackerhttp.dto.Task;
import org.springframework.stereotype.Component;

@Component //Service - для бинов, которые выполняют сервисную логику; Configuration - для тех кто создает другие бины; Repository - для тех кто работает с хралищами данных
public class ConnectionService {
    ConnectionService(Task task) {
        System.out.println("ConnectionService");
        System.out.println(task);
    }

    public void makeConnection() {
        System.out.println("УСТАНОВКА СОЕДИНЕНИЯ");
    }
}
