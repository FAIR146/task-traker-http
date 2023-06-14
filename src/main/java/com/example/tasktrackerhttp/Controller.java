package com.example.tasktrackerhttp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final Manager manager;
    public Controller (Manager manager) {
        this.manager = manager;
    }

    @GetMapping("/addTask/{name}/{description}/{status}")
        public long addTask (@PathVariable String name,@PathVariable String description,@PathVariable Status status) {
        return manager.addTask(name,description, status);
    }
    @GetMapping("/addEpic/{name}/{description}")
    public long addEpic (@PathVariable String name, @PathVariable String description) {
        return manager.addEpic(name, description);
    }
//    @GetMapping("/addSubTask/{id}/{name}/{description}/{status}")
//    public SubTask addSubTask (@PathVariable long id ,@PathVariable String name, @PathVariable String description, @PathVariable Status status) {
//        return addSubTask(id, name)
//    }
    @GetMapping("/removeEpicById/{id}")
    public void removeEpicById (long id) {

    }
    @GetMapping("/removeTaskById/{od}")
    public void removeTaskById (long id) {

    }
    @GetMapping("/removeSubTaskById/{id}")
    public void removeSubTaskById (long id) {

    }
    @GetMapping("/removeAllTasks")
    public void removeAllTasks () {

    }
    @GetMapping("/removeAllEpics")
    public void removeAllEpics () {

    }
    @GetMapping("/removeAllSubTasks")
    public void removeAllSubTasks () {

    }
    @GetMapping("/getEpicById")
    public void getEpicById () {

    }

}
