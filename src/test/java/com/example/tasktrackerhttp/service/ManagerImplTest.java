package com.example.tasktrackerhttp.service;
import com.example.tasktrackerhttp.BaseTest;
import com.example.tasktrackerhttp.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ManagerImplTest extends BaseTest {

    @Autowired
    private Manager manager;

    @Test
    void addTaskNewStatus() {
        String name = "Сходить в школу";
        String description = "Собарться и пойти в школу";
        Status status = Status.NEW;
        long idTask = manager.addTask(name, description, status, TEST_USERNAME);
        Task task = manager.getTaskById(idTask);
        Assertions.assertEquals(idTask, task.getId());
        Assertions.assertEquals(name, task.getName());
        Assertions.assertEquals(description, task.getDescription());
        Assertions.assertEquals(status, task.getStatus());
    }

    @Test
    void addTaskDoneStatus() {
        String name = "Сходить в школу";
        String description = "Собарться и пойти в школу";
        Status status = Status.DONE;
        long idTask = manager.addTask(name, description, status, TEST_USERNAME);
        Task task = manager.getTaskById(idTask);
        Assertions.assertEquals(idTask, task.getId());
        Assertions.assertEquals(name, task.getName());
        Assertions.assertEquals(description, task.getDescription());
        Assertions.assertEquals(status, task.getStatus());
    }

    @Test
    void addTaskInProgressStatus() {
        String name = "имя";
        String description = "описание";
        Status status = Status.IN_PROGRESS;
        long idTask = manager.addTask(name, description,status, TEST_USERNAME);
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
        long idEpic = manager.addEpic(name,description, TEST_USERNAME);
        Epic epic = manager.getEpicById(idEpic);
        List<SubTask> subTasks = epic.getSubTasks();
        Assertions.assertTrue(subTasks.isEmpty());
        Assertions.assertNotNull(epic);
        Assertions.assertEquals(idEpic, epic.getId());
        Assertions.assertEquals(name, epic.getName());
        Assertions.assertEquals(description, epic.getDescription());
    }

    @Test
    void addSubTaskNew() {
        long epicId = manager.addEpic("1", "1", TEST_USERNAME);

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
    void addSubTaskInProgress() {
        long epicId = manager.addEpic("1", "1", TEST_USERNAME);

        String name = "Одеться";
        String description = "Встать";
        Status status = Status.IN_PROGRESS;
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
    void addSubTaskDone() {
        long epicId = manager.addEpic("1", "1", TEST_USERNAME);

        String name = "Одеться";
        String description = "Встать";
        Status status = Status.DONE;
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
        long idEpic = manager.addEpic("1", "1", TEST_USERNAME);
        long idSubTask = manager.addSubTask(idEpic, "2", "2", Status.NEW);
        manager.removeEpicById(idEpic);

        Epic epic = manager.getEpicById(idEpic);
        SubTask subTask = manager.getSubTaskById(idSubTask);
        Assertions.assertNull(epic);
        Assertions.assertNull(subTask);
    }

    @Test
    void removeTaskById() {
        long idTask = manager.addTask("1", "1", Status.NEW, TEST_USERNAME);
        Task task = manager.getTaskById(idTask);
        Assertions.assertNotNull(task);
        manager.removeTaskById(idTask);
        Task task1 = manager.getTaskById(idTask);
        Assertions.assertNull(task1);
    }


    @Test
    void removeSubTaskById() {
        long idEpic =  manager.addEpic("1", "1", TEST_USERNAME);
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
    void getEpicByIdNewStatus() {
        String name = "name";
        String description = "description";
        long idEpic = manager.addEpic(name, description, TEST_USERNAME);
        Epic epic = manager.getEpicById(idEpic);

        Assertions.assertEquals(idEpic, epic.getId());
        Assertions.assertEquals(name, epic.getName());

        String subName1 = "subName1";
        String subDesc1 = "subDesc1";
        long idSubTask1 = manager.addSubTask(idEpic, subName1, subDesc1, Status.NEW);
        SubTask subTask1 = manager.getSubTaskById(idSubTask1);


        String subName2 = "subName2";
        String subDesc2 = "subDesc2";
        long idSubTask2 = manager.addSubTask(idEpic, subName2, subDesc2, Status.NEW);
        SubTask subTask2 = manager.getSubTaskById(idSubTask2);

        List<SubTask> subTasks = new ArrayList<>();
        subTasks.add(subTask1);
        subTasks.add(subTask2);
        epic.setSubTasks(subTasks);

        Assertions.assertEquals(Status.NEW, manager.getEpicStatus(epic));
    }

    @Test
    void getEpicByIdInProgressStatus() {
        String name = "name";
        String description = "description";
        long idEpic = manager.addEpic(name, description, TEST_USERNAME);
        Epic epic = manager.getEpicById(idEpic);

        Assertions.assertEquals(idEpic, epic.getId());
        Assertions.assertEquals(name, epic.getName());

        String subName1 = "subName1";
        String subDesc1 = "subDesc1";
        long idSubTask1 = manager.addSubTask(idEpic, subName1, subDesc1, Status.IN_PROGRESS);
        SubTask subTask1 = manager.getSubTaskById(idSubTask1);


        String subName2 = "subName2";
        String subDesc2 = "subDesc2";
        long idSubTask2 = manager.addSubTask(idEpic, subName2, subDesc2, Status.IN_PROGRESS);
        SubTask subTask2 = manager.getSubTaskById(idSubTask2);

        List<SubTask> subTasks = new ArrayList<>();
        subTasks.add(subTask1);
        subTasks.add(subTask2);
        epic.setSubTasks(subTasks);

        Assertions.assertEquals(Status.IN_PROGRESS, manager.getEpicStatus(epic));
    }

    @Test
    void getEpicByIdDoneStatus() {
        String name = "name";
        String description = "description";
        long idEpic = manager.addEpic(name, description, TEST_USERNAME);
        Epic epic = manager.getEpicById(idEpic);

        Assertions.assertEquals(idEpic, epic.getId());
        Assertions.assertEquals(name, epic.getName());

        String subName1 = "subName1";
        String subDesc1 = "subDesc1";
        long idSubTask1 = manager.addSubTask(idEpic, subName1, subDesc1, Status.DONE);
        SubTask subTask1 = manager.getSubTaskById(idSubTask1);


        String subName2 = "subName2";
        String subDesc2 = "subDesc2";
        long idSubTask2 = manager.addSubTask(idEpic, subName2, subDesc2, Status.DONE);
        SubTask subTask2 = manager.getSubTaskById(idSubTask2);

        List<SubTask> subTasks = new ArrayList<>();
        subTasks.add(subTask1);
        subTasks.add(subTask2);
        epic.setSubTasks(subTasks);

        Assertions.assertEquals(Status.DONE, manager.getEpicStatus(epic));
    }

    @Test
    void getTaskById() {
        String name = "1";
        String description = "1";
        Status status = Status.NEW;
        long idTask = manager.addTask(name, description, status, TEST_USERNAME);
        Task task1 = manager.getTaskById(idTask);
        Assertions.assertEquals(name, task1.getName());
        Assertions.assertEquals(description, task1.getDescription());
        Assertions.assertEquals(status, task1.getStatus());
    }

    @Test
    void getSubTaskById() {
        long idEpic = manager.addEpic("1", "1", TEST_USERNAME);

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
        String description = "description1";
        Status status = Status.NEW;
        long idTask = manager.addTask(name1, description, status, TEST_USERNAME);
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
        long idEpic = manager.addEpic(name1, description, TEST_USERNAME);
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
        long idEpic = manager.addEpic("1", "1", TEST_USERNAME);
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
    @Test
    void getAllCreatedTasksByUser () {
        long idTask1 = manager.addTask("name11", "get all with new status", Status.NEW, TEST_USERNAME);
        long idTask2 = manager.addTask("name2", "get all with in progress status", Status.IN_PROGRESS, TEST_USERNAME);
        long idTask3 = manager.addTask("name3", "get all with done status", Status.DONE, TEST_USERNAME);

        GetAllCreatedTasksByUser getAllCreatedTasksByUser = manager.getAllCreatedTasksByUser(TEST_USERNAME);

        Assertions.assertTrue(getAllCreatedTasksByUser.getNewTasks().stream().anyMatch(task -> task.getId() == idTask1));
        Assertions.assertTrue(getAllCreatedTasksByUser.getNewTasks().stream().allMatch(task -> task.getStatus() == Status.NEW));

        Assertions.assertTrue(getAllCreatedTasksByUser.getInProgressTasks().stream().anyMatch(task -> task.getId() == idTask2));
        Assertions.assertTrue(getAllCreatedTasksByUser.getInProgressTasks().stream().allMatch(task -> task.getStatus() == Status.IN_PROGRESS));

        Assertions.assertTrue(getAllCreatedTasksByUser.getDoneTasks().stream().anyMatch(task -> task.getId() == idTask3));
        Assertions.assertTrue(getAllCreatedTasksByUser.getDoneTasks().stream().allMatch(task -> task.getStatus() == Status.DONE));
    }

    @Test
    void getAllCreatedEpicsByUser () {
        Epic epicNew = createEpic("epic1", Status.NEW);
        Epic epicInProgress = createEpic("epic2", Status.IN_PROGRESS);
        Epic epicDone = createEpic("epic3", Status.DONE);

        GetAllCreatedEpicsByUser getAllCreatedEpicsByUser = manager.getAllCreatedEpicsByUser(TEST_USERNAME);

        Assertions.assertTrue(getAllCreatedEpicsByUser.getDoneEpics().contains(epicDone));
        Assertions.assertTrue(getAllCreatedEpicsByUser.getDoneEpics().stream().allMatch(epic -> manager.getEpicStatus(epic) == Status.DONE));

        Assertions.assertTrue(getAllCreatedEpicsByUser.getInProgressEpics().contains(epicInProgress));
        Assertions.assertTrue(getAllCreatedEpicsByUser.getInProgressEpics().stream().allMatch(epic -> manager.getEpicStatus(epic) == Status.IN_PROGRESS));

        Assertions.assertTrue(getAllCreatedEpicsByUser.getNewEpics().contains(epicNew));
        Assertions.assertTrue(getAllCreatedEpicsByUser.getNewEpics().stream().allMatch(epic -> manager.getEpicStatus(epic) == Status.NEW));
    }

    private Epic createEpic(String epicName, Status subtasksStatus) {
        long idEpicNew = manager.addEpic(epicName, "desc1", TEST_USERNAME);
        manager.addSubTask(idEpicNew,"subName1", "subDesc1", subtasksStatus);
        manager.addSubTask(idEpicNew,"subName2", "subDesc2", subtasksStatus);
        return manager.getEpicById(idEpicNew);
    }
}

