package com.example.tasktrackerhttp.experiment;

import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Task createTask() {
        Task task = new Task();
        task.setId(123);
        task.setName("task 123");
        task.setDescription("desc");
        task.setStatus(Status.NEW);
        return task;
    }
}
