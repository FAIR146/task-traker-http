package com.example.tasktrackerhttp.service;
import com.example.tasktrackerhttp.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ManagerImplTest {

    @Autowired
    private Manager manager;

    @Test
    void addTask() {
        String name = "Сходить в школу";
        String userName = "Антон";
        String description = "Собарться и пойти в школу";
        Status status = Status.NEW;
        long idTask = manager.addTask(name, description, status, userName);
        Task task = manager.getTaskById(idTask);
        Assertions.assertEquals(idTask, task.getId());
        Assertions.assertEquals(name, task.getName());
        Assertions.assertEquals(description, task.getDescription());
        Assertions.assertEquals(status, task.getStatus());
    }

    @Test
    void addEpic() {
        String name = "Поехать в лес";
        String userName = "жегя";
        String description = "Собрать вещи";
        long idEpic = manager.addEpic(name,description, userName);
        Epic epic = manager.getEpicById(idEpic);
        List<SubTask> subTasks = epic.getSubTasks();
        Assertions.assertTrue(subTasks.isEmpty());
        Assertions.assertNotNull(epic);
        Assertions.assertEquals(idEpic, epic.getId());
        Assertions.assertEquals(name, epic.getName());
        Assertions.assertEquals(description, epic.getDescription());
    }

    @Test
    void addSubTask() {
        long epicId = manager.addEpic("1", "1", "fff");

        String name = "Одеться";
        String description = "Встать";
        Status status = Status.NEW;
        long idSubTask = manager.addSubTask(epicId, name, description, status);
        SubTask subTask = manager.getSubTaskById(idSubTask);

        Assertions.assertEquals(epicId, subTask.getEpicId());
        Assertions.assertEquals(name, subTask.getName());
        Assertions.assertEquals(description, subTask.getDescription());
        Assertions.assertEquals(status, subTask.getStatus());

        Epic epic = manager.getEpicById(epicId);

        List<SubTask> subTasks = epic.getSubTasks();


        Assertions.assertTrue(subTasks.stream().anyMatch(st -> st.getId() == idSubTask));

    }

    @Test
    void removeEpicById() {
        long idEpic = manager.addEpic("1", "1", "anna");
        long idSubTask = manager.addSubTask(idEpic, "2", "2", Status.NEW);
        manager.removeEpicById(idEpic);

        Epic epic = manager.getEpicById(idEpic);
        SubTask subTask = manager.getSubTaskById(idSubTask);
        Assertions.assertNull(epic);
        Assertions.assertNull(subTask);
    }

    @Test
    void removeTaskById() {
        long idTask = manager.addTask("1", "1", Status.NEW, "Никита");
        Task task = manager.getTaskById(idTask);
        Assertions.assertNotNull(task);
        manager.removeTaskById(idTask);
        Task task1 = manager.getTaskById(idTask);
        Assertions.assertNull(task1);
    }


    @Test
    void removeSubTaskById() {
        long idEpic =  manager.addEpic("1", "1", "ura");
        long idSubTask = manager.addSubTask(idEpic, "2" , "2", Status.NEW);
        Epic epic = manager.getEpicById(idEpic);
        List<SubTask> subTasks = epic.getSubTasks();
        Assertions.assertTrue(subTasks.stream().anyMatch(subTask -> subTask.getId() == idSubTask));
        manager.removeSubTaskById(idSubTask);
        SubTask subTask1 = manager.getSubTaskById(idSubTask);
        Assertions.assertNull(subTask1);

        Assertions.assertFalse(manager.getEpicById(idEpic).getSubTasks().stream().anyMatch(subTask -> subTask.getId() == idSubTask));

    }
    @Test
    void getEpicById() {
        String name = "1";
        String userName = "Tolya";
        String description = "1";
        long idEpic = manager.addEpic(name, description, userName);
        Epic epic1 = manager.getEpicById(idEpic);
        List<SubTask> subTasks = epic1.getSubTasks();
        List<Status> statuses = new ArrayList<>();

        Assertions.assertNotNull(epic1);
    }

    @Test
    void getTaskById() {
        String name = "1";
        String userName = "Валера";
        String description = "1";
        Status status = Status.NEW;
        long idTask = manager.addTask(name, description, status,userName);
        Task task1 = manager.getTaskById(idTask);
        Assertions.assertEquals(name, task1.getName());
        Assertions.assertEquals(description, task1.getDescription());
        Assertions.assertEquals(status, task1.getStatus());
    }

    @Test
    void getSubTaskById() {
        long idEpic = manager.addEpic("1", "1", "maks");

        String name = "1";
        String description = "1";
        Status status = Status.NEW;
        long idSubTask = manager.addSubTask(idEpic ,name, description, status);

        SubTask subTask = manager.getSubTaskById(idSubTask);

        Assertions.assertEquals(idSubTask, subTask.getId());
        Assertions.assertEquals(idEpic, subTask.getEpicId());
        Assertions.assertEquals(name, subTask.getName());
        Assertions.assertEquals(description, subTask.getDescription());
        Assertions.assertEquals(status, subTask.getStatus());
    }

    @Test
    void updateTask() {
        String name1 = "name1";
        String userName1 = "Дима";
        String description = "description1";
        Status status = Status.NEW;
        long idTask = manager.addTask(name1, description, status, userName1);
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
        String userName1 = "stas";
        String description = "description1";
        long idEpic = manager.addEpic(name1, description, userName1);
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
        long idEpic = manager.addEpic("1", "1", "valera");
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

