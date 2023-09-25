package com.example.tasktrackerhttp.experiment;

import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Task;
import org.springframework.stereotype.Service;

@Service
public class InternetService {
    private final ConnectionService connectionService;

    public InternetService(ConnectionService connectionService, Task task) {
        this.connectionService = connectionService;
        connectionService.makeConnection();

        System.out.println(task);
    }

    public void makeRequest() {
        System.out.println("ОТПРАВЛЕН ЗАПРОС В ИНТЕРНЕТ");
    }
}
