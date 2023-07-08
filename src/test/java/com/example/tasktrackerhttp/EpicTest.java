package com.example.tasktrackerhttp;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EpicTest {

    @Test
    void getStatusInProgress1() {
        Epic epic = new Epic();
        SubTask subTask = new SubTask(epic);
        SubTask subTask1 = new SubTask(epic);
        SubTask subTask2 = new SubTask(epic);
        SubTask subTask3 = new SubTask(epic);
        subTask.setStatus(Status.NEW);
        subTask1.setStatus(Status.NEW);
        subTask2.setStatus(Status.DONE);
        subTask3.setStatus(Status.IN_PROGRESS);
        epic.addSubTask(subTask);
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        epic.addSubTask(subTask3);
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }
    @Test
    void getStatusInProgress2() {
        Epic epic = new Epic();
        SubTask subTask = new SubTask(epic);
        SubTask subTask1 = new SubTask(epic);
        SubTask subTask2 = new SubTask(epic);
        SubTask subTask3 = new SubTask(epic);
        subTask.setStatus(Status.NEW);
        subTask1.setStatus(Status.NEW);
        subTask2.setStatus(Status.DONE);
        subTask3.setStatus(Status.DONE);
        epic.addSubTask(subTask);
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        epic.addSubTask(subTask3);
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }
    @Test
    void getStatusDone () {
        Epic epic = new Epic();
        SubTask subTask = new SubTask(epic);
        SubTask subTask1 = new SubTask(epic);
        SubTask subTask2 = new SubTask(epic);
        SubTask subTask3 = new SubTask(epic);
        subTask.setStatus(Status.DONE);
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        subTask3.setStatus(Status.DONE);
        epic.addSubTask(subTask);
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        epic.addSubTask(subTask3);
        Assertions.assertEquals(Status.DONE, epic.getStatus());
    }
    @Test
    void getStatusNew () {
        Epic epic = new Epic();
        SubTask subTask = new SubTask(epic);
        SubTask subTask1 = new SubTask(epic);
        SubTask subTask2 = new SubTask(epic);
        SubTask subTask3 = new SubTask(epic);
        subTask.setStatus(Status.NEW);
        subTask1.setStatus(Status.NEW);
        subTask2.setStatus(Status.NEW);
        subTask3.setStatus(Status.NEW);
        epic.addSubTask(subTask);
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        epic.addSubTask(subTask3);
        Assertions.assertEquals(Status.NEW, epic.getStatus());
    }
}
