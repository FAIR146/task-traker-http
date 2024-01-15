package com.example.tasktrackerhttp.service;
import com.example.tasktrackerhttp.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootTest
public class ManagerImplTest {

    @Autowired
    private Manager manager;

    @Test
    void addTask() {
        String name = "Сходить в школу";
        String description = "Собарться и пойти в школу";
        Status status = Status.NEW;
        long idTask = manager.addTask(name, description, status);
        Task task = manager.getTaskById(idTask);
        Assertions.assertEquals(idTask, task.getId());
        Assertions.assertEquals(name, task.getName());
        Assertions.assertEquals(description, task.getDescription());
        Assertions.assertEquals(status, task.getStatus());
    }

    @Test
    void addEpic() {
        String name = "Поехать в лес";
        String description = "Собрать вещи";
        long idEpic = manager.addEpic(name,description);
        Epic epic = manager.getEpicById(idEpic);
        List<Long> idSubTasks = epic.getSubTasksId();
        Assertions.assertTrue(idSubTasks.isEmpty());
        Assertions.assertNotNull(epic);
        Assertions.assertEquals(idEpic, epic.getId());
        Assertions.assertEquals(name, epic.getName());
        Assertions.assertEquals(description, epic.getDescription());
    }

    @Test
    void addSubTask() {
        long epicId = manager.addEpic("1", "1");
        Epic epic = manager.getEpicById(epicId);
        List<Long> subTasksId = epic.getSubTasksId();
        String name = "Одеться";
        String description = "Встать";
        Status status = Status.NEW;
        long idSubTask = manager.addSubTask(epicId, name, description, status);
        SubTask subTask = manager.getSubTaskById(idSubTask);
        Assertions.assertEquals(epicId, subTask.getEpicId());
        Assertions.assertEquals(name, subTask.getName());
        Assertions.assertEquals(description, subTask.getDescription());
        Assertions.assertEquals(status, subTask.getStatus());

        Epic epicUpdate = manager.getEpicById(epicId);
        List<Long> subTasksIdUpd = epicUpdate.getSubTasksId();
        Assertions.assertTrue(subTasksIdUpd.stream().anyMatch( id -> id == idSubTask));
        Assertions.assertNotNull(subTasksId.stream().anyMatch(id -> id == idSubTask));

    }

    @Test
    void removeEpicById() {
        long idEpic = manager.addEpic("1", "1");
        long idSubTask = manager.addSubTask(idEpic, "2", "2", Status.NEW);
        Epic epic = manager.getEpicById(idEpic);
        SubTask subTask = manager.getSubTaskById(idSubTask);
        List<Long> subtasksId = epic.getSubTasksId();
        epic.setSubTasksId(subtasksId);
        Assertions.assertTrue(subtasksId.stream().anyMatch(id -> id == idSubTask));
        Assertions.assertNotNull(epic);
        Assertions.assertNotNull(subTask);
        manager.removeSubTaskById(idSubTask);
        manager.removeEpicById(idEpic);
        Epic epic1 = manager.getEpicById(idEpic);
        SubTask subTask1 = manager.getSubTaskById(idSubTask);
        Assertions.assertNull(epic1);
        Assertions.assertNull(subTask1);
    }

    @Test
    void removeTaskById() {
        long idTask = manager.addTask("1", "1", Status.NEW);
        Task task = manager.getTaskById(idTask);
        Assertions.assertNotNull(task);
        manager.removeTaskById(idTask);
        Task task1 = manager.getTaskById(idTask);
        Assertions.assertNull(task1);
    }


    @Test
    void removeSubTaskById() {
        long idEpic =  manager.addEpic("1", "1");
        long idSubTask = manager.addSubTask(idEpic, "2" , "2", Status.NEW);
        Epic epic = manager.getEpicById(idEpic);
        List<Long> subTasksId = epic.getSubTasksId();
        Assertions.assertTrue(subTasksId.stream().anyMatch(id -> id == idSubTask));
        manager.removeSubTaskById(idSubTask);
        SubTask subTask1 = manager.getSubTaskById(idSubTask);
        Assertions.assertNull(subTask1);
    }
    @Test
    void getEpicById() {
        String name = "1";
        String description = "1";
        long idEpic = manager.addEpic(name, description);
        Epic epic1 = manager.getEpicById(idEpic);
        List<Long> subTasks = epic1.getSubTasksId();
        List<Status> statuses = new ArrayList<>();

        Assertions.assertNotNull(epic1);
    }

    @Test
    void getTaskById() {
        String name = "1";
        String description = "1";
        Status status = Status.NEW;
        long idTask = manager.addTask(name, description, status);
        Task task1 = manager.getTaskById(idTask);
        Assertions.assertEquals(name, task1.getName());
        Assertions.assertEquals(description, task1.getDescription());
        Assertions.assertEquals(status, task1.getStatus());
    }

    @Test
    void getSubTaskById() {
        long idEpic = manager.addEpic("1", "1");
        Epic epic = manager.getEpicById(idEpic);
        String name = "1";
        String description = "1";
        Status status = Status.NEW;
        long idSubTask = manager.addSubTask(idEpic ,name, description, status);
        SubTask subTask1 = manager.getSubTaskById(idSubTask);
        epic = manager.getEpicById(idEpic);
        List<Long> subTasksId = epic.getSubTasksId();
        Assertions.assertTrue(subTasksId.stream().anyMatch(subTask -> subTask == idSubTask));
        Assertions.assertEquals(idEpic, subTask1.getEpicId());
        Assertions.assertEquals(name, subTask1.getName());
        Assertions.assertEquals(description, subTask1.getDescription());
        Assertions.assertEquals(status, subTask1.getStatus());
    }

    @Test
    void updateTask() {
        String name1 = "name1";
        String description = "description1";
        Status status = Status.NEW;
        long idTask = manager.addTask(name1, description, status);
        Task taskOriginal = manager.getTaskById(idTask);
        Assertions.assertNotNull(taskOriginal);
        String name2 = "name2";
        String description2 = "description2";
        manager.updateTask(idTask, name2, description2, status);
        Task taskUpdated = manager.getTaskById(idTask);
        Assertions.assertEquals(name2, taskUpdated.getName());
        Assertions.assertEquals(description2, taskUpdated.getDescription());
        Assertions.assertEquals(status, taskUpdated.getStatus());
    }

    @Test
    void updateEpic() {
        String name1 = "name1";
        String description = "description1";
        long idEpic = manager.addEpic(name1, description);
        Epic epicOriginal = manager.getEpicById(idEpic);
        Assertions.assertNotNull(epicOriginal);
        String name2 = "name2";
        manager.updateEpic(idEpic, name2,description);
        Epic epicUpdated = manager.getEpicById(idEpic);
        Assertions.assertEquals(name2, epicUpdated.getName());
        Assertions.assertEquals(description, epicUpdated.getDescription());
    }

    @Test
    void updateSubTask() {
        long idEpic = manager.addEpic("1", "1");
        String name1 = "1";
        String description = "1";
        Status status = Status.NEW;
        long idSubTask = manager.addSubTask(idEpic, name1, description, status);
        SubTask subTaskOriginal = manager.getSubTaskById(idSubTask);
        Assertions.assertNotNull(subTaskOriginal);
        String name2 = "2";
        manager.updateSubTask(idSubTask, name2, description, status);
        SubTask subTaskUpdated = manager.getSubTaskById(idSubTask);
        Assertions.assertEquals(name2,subTaskUpdated.getName());
        Assertions.assertEquals(description, subTaskUpdated.getDescription());
        Assertions.assertEquals(status, subTaskUpdated.getStatus());
    }

}

