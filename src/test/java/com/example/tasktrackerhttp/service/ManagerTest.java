package com.example.tasktrackerhttp.service;
import com.example.tasktrackerhttp.dto.*;
import com.example.tasktrackerhttp.service.Manager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ManagerTest {
    Manager manager = new Manager();
    @Test
    public void answer () {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(2);
        boolean contains = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == 5) {
                contains = true;
                break;
            }
        }
        if (contains) {
            System.out.println("Число есть");
        } else {
            System.out.println("Числа нету");
        }
    }
    @Test
    public void answer2 () {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(2);
        boolean contains = list.stream()
                .anyMatch(number -> number == 5);
    }

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
        Assertions.assertEquals(idEpic, epic.getId());
        Assertions.assertEquals(name, epic.getName());
        Assertions.assertEquals(description, epic.getDescription());;
    }

    @Test
    void addSubTask() {
        long epicId = manager.addEpic("1", "1");
        Epic epic = manager.getEpicById(epicId);
        Assertions.assertNotNull(epic);
        String name = "Одеться";
        String description = "Встать";
        Status status = Status.NEW;
        long idSubTask = manager.addSubTask(epicId, name, description, status);
        SubTask subTask = manager.getSubTaskById(idSubTask);
        Assertions.assertEquals(name, subTask.getName());
        Assertions.assertEquals(description, subTask.getDescription());
        Assertions.assertEquals(status, subTask.getStatus());
    }

    @Test
    void removeEpicById() {
        String name = "1";
        String description = "1";
        long idEpic = manager.addEpic(name, description);
        Epic epic = manager.getEpicById(idEpic);
        Assertions.assertNotNull(epic);
        manager.removeEpicById(idEpic);
        Epic epic1 = manager.getEpicById(idEpic);
        Assertions.assertNull(epic1);
    }

    @Test
    void removeTaskById() {
        long idTask = manager.addTask("1", "1", Status.NEW);
        Task task = manager.getTaskById(idTask);
        Assertions.assertNotNull(task);
        manager.removeTaskById(idTask);
        Task task1 = manager.getTaskById(idTask);
        manager.getTaskById(idTask);
        Assertions.assertNull(task1);
    }

    @Test
    void removeSubTaskById() {
        long idEpic =  manager.addEpic("1", "1");
        long idSubTask = manager.addSubTask(idEpic, "2" , "2", Status.NEW);
        Epic epic = manager.getEpicById(idEpic);
        List<SubTask> subTasks = epic.getSubTasks();
        Assertions.assertTrue(subTasks.stream().anyMatch(subTask -> subTask.getId() == idSubTask));
        manager.removeSubTaskById(idSubTask);
        SubTask subTask1 = manager.getSubTaskById(idSubTask);
        Assertions.assertNull(subTask1);
        Assertions.assertFalse(epic.getSubTasks().stream().anyMatch(subTask -> subTask.getId() == idSubTask));
    }

    @Test
    void removeAllEpics() {
        String name1 = "1";
        String description1 = "1";
        String name2 = "2";
        String description2 = "2";
        long idEpic1 = manager.addEpic(name1, description1);
        long idEpic2 = manager.addEpic(name2, description2);
        Epic epic1 = manager.getEpicById(idEpic1);
        Epic epic2 =  manager.getEpicById(idEpic2);
        Assertions.assertNotNull(epic1);
        Assertions.assertNotNull(epic2);
        manager.removeAllEpics();
        Epic epicFirst = manager.getEpicById(idEpic1);
        Epic epicSecond = manager.getEpicById(idEpic2);
        manager.getAllEpics();
        Assertions.assertNull(epicFirst);
        Assertions.assertNull(epicSecond);
    }

    @Test
    void removeAllSubTasks() {
        long idEpic1 = manager.addEpic("epic1","epic1");
        long idEpic2 = manager.addEpic("epic2", "epic2");
        String name1 = "1";
        String description1 = "1";
        String name2 = "2";
        String description2 = "2";
        long idSubTask1 = manager.addSubTask(idEpic1, name1, description1, Status.NEW);
        long idSubtask2 = manager.addSubTask(idEpic2, name2, description2, Status.NEW);
        SubTask subTask1 = manager.getSubTaskById(idSubTask1);
        SubTask subTask2 = manager.getSubTaskById(idSubtask2);
        Assertions.assertNotNull(subTask1);
        Assertions.assertNotNull(subTask2);
        manager.removeAllSubTasks();
        SubTask subTaskFirst = manager.getSubTaskById(idSubTask1);
        SubTask subTaskSecond = manager.getSubTaskById(idSubtask2);
        manager.getAllSubTasks();
        Assertions.assertNull(subTaskFirst);
        Assertions.assertNull(subTaskSecond);
    }

    @Test
    void removeAllTasks() {
        String name1 = "1";
        String description1 = "1";
        String name2 = "2";
        String description2 = "2";
        long idTask1 = manager.addTask(name1, description1, Status.NEW);
        long idTask2 = manager.addTask(name2, description2, Status.NEW);
        Task task1 = manager.getTaskById(idTask1);
        Task task2 = manager.getTaskById(idTask2);
        Assertions.assertNotNull(task1);
        Assertions.assertNotNull(task2);
        manager.removeAllTasks();
        Task taskFirst = manager.getSubTaskById(idTask1);
        Task taskSecond = manager.getTaskById(idTask2);
        manager.getAllTasks();
        Assertions.assertNull(taskFirst);
        Assertions.assertNull(taskSecond);
    }

    @Test
    void getEpicById() {
        String name = "1";
        String description = "1";
        long idEpic = manager.addEpic(name, description);
        Epic epic1 = manager.getEpicById(idEpic);
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
        String name = "1";
        String description = "1";
        Status status = Status.NEW;
        long idSubTask = manager.addSubTask(idEpic ,name, description, status);
        SubTask subTask1 = manager.getSubTaskById(idSubTask);
        Assertions.assertEquals(name, subTask1.getName());
        Assertions.assertEquals(description, subTask1.getDescription());
        Assertions.assertEquals(status, subTask1.getStatus());
    }

    @Test
    void getAllEpics() {
        String name1 = "1";
        String description1 = "1";
        String name2 = "2";
        String description2 = "2";
        long idEpic1 = manager.addEpic(name1, description1);
        long idEpic2 = manager.addEpic(name2, description2);
        Epic epicFirst = manager.getEpicById(idEpic1);
        Epic epicSecond = manager.getEpicById(idEpic2);
        manager.getAllEpics();
        Assertions.assertEquals(name1, epicFirst.getName());
        Assertions.assertEquals(description1, epicFirst.getDescription());
        Assertions.assertEquals(name2, epicSecond.getName());
        Assertions.assertEquals(description2, epicSecond.getDescription());
    }

    @Test
    void getAllSubTasks() {
        long idEpic1 = manager.addEpic("epic1","epic1");
        long idEpic2 = manager.addEpic("epic2", "epic2");
        String name1 = "1";
        String description1 = "1";
        String name2 = "2";
        String description2 = "2";
        Status status1 = Status.NEW;
        Status status2 = Status.NEW;
        long idSubTask1 = manager.addSubTask(idEpic1, name1, description1, status1);
        long idSubTask2 = manager.addSubTask(idEpic2, name2, description2, status2);
        SubTask subTaskFirst = manager.getSubTaskById(idSubTask1);
        SubTask subTaskSecond = manager.getSubTaskById(idSubTask2);
        manager.getAllEpics();
        Assertions.assertEquals(name1, subTaskFirst.getName());
        Assertions.assertEquals(description1, subTaskFirst.getDescription());
        Assertions.assertEquals(status1, subTaskFirst.getStatus());
        Assertions.assertEquals(name2, subTaskSecond.getName());
        Assertions.assertEquals(description2, subTaskSecond.getDescription());
        Assertions.assertEquals(status2, subTaskSecond.getStatus());

    }

    @Test
    void getAllTasks() {
        String name1 = "1";
        String description1 = "1";
        Status status1 = Status.NEW;
        String name2 = "2";
        String description2 = "2";
        Status status2 = Status.NEW;
        long idTask1 = manager.addTask(name1, description1, status1);
        long idTask2 = manager.addTask(name2, description2, status2);
        Task taskFirst = manager.getTaskById(idTask1);
        Task taskSecond = manager.getTaskById(idTask2);
        manager.getAllTasks();
        Assertions.assertEquals(name1, taskFirst.getName());
        Assertions.assertEquals(description1, taskFirst.getDescription());
        Assertions.assertEquals(status1, taskFirst.getStatus());
        Assertions.assertEquals(name2, taskSecond.getName());
        Assertions.assertEquals(description2, taskSecond.getDescription());
        Assertions.assertEquals(status2, taskSecond.getStatus());
    }

    @Test
    void updateTask() {
        String name1 = "1";
        String description = "1";
        Status status = Status.NEW;
        long idTask = manager.addTask(name1, description, status);
        Task task1 = manager.getTaskById(idTask);
        Assertions.assertNotNull(task1);
        String name2 = "2";
        task1.setName(name2   );
        Assertions.assertEquals(name2, task1.getName());
        Assertions.assertEquals(description, task1.getDescription());
        Assertions.assertEquals(status, task1.getStatus());
    }

    @Test
    void updateEpic() {
        String name1 = "1";
        String description = "1";
        long idEpic = manager.addEpic(name1, description);
        Epic epic1 = manager.getEpicById(idEpic);
        Assertions.assertNotNull(epic1);
        String name2 = "2";
        epic1.setName(name2);
        Assertions.assertEquals(name2, epic1.getName());
        Assertions.assertEquals(description, epic1.getDescription());
    }

    @Test
    void updateSubTask() {
        long idEpic = manager.addEpic("1", "1");
        String name1 = "1";
        String description = "1";
        Status status = Status.NEW;
        long idSubTask = manager.addSubTask(idEpic, name1, description, status);
        SubTask subTask = manager.getSubTaskById(idSubTask);
        Assertions.assertNotNull(subTask);
        String name2 = "2";
        subTask.setName(name2);
        Assertions.assertEquals(name2,subTask.getName());
        Assertions.assertEquals(description, subTask.getDescription());
        Assertions.assertEquals(status, subTask.getStatus());
    }

}

