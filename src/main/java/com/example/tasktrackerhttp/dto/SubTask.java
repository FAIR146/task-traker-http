package com.example.tasktrackerhttp.dto;


import jakarta.validation.constraints.NotNull;

public class SubTask extends EntityWithStatus {

    @NotNull
    private long epicId;

    public void setEpicId (long epicId) {
        this.epicId = epicId;
    }
    public long getEpicId () {
        return epicId;
    }


    @Override
    public String toString() {
        return "SubTask{" + System.lineSeparator() +
                "epicId=" + epicId + " " + System.lineSeparator() +
                "id=" + getId() + System.lineSeparator() +
                "name=" + getName() + System.lineSeparator() +
                "description=" + getDescription() +  System.lineSeparator() +
                "status=" + getStatus() +  System.lineSeparator() +
                '}';
    }
}


