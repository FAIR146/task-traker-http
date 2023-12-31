package com.example.tasktrackerhttp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SubTask extends Task {
    @JsonIgnore
    private final Epic epic;
    public SubTask (Epic epic) {
        this.epic = epic;
    }
    public Epic getEpic () {
        return epic;
    }

    @Override
    public String toString() {
        return "SubTask{" + System.lineSeparator() +
                "epic=" + epic.getId() + " " + epic.getName()+ " " + System.lineSeparator() +
                "id=" + getId() + System.lineSeparator() +
                "name=" + getName() + System.lineSeparator() +
                "description=" + getDescription() +  System.lineSeparator() +
                "status=" + getStatus() +  System.lineSeparator() +
                '}';
    }
}


