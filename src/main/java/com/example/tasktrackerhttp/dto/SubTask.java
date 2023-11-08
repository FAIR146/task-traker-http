package com.example.tasktrackerhttp.dto;



public class SubTask extends Task {

    private int epicId;

    public void setEpicId (int epicId) {
        this.epicId = epicId;
    }
    public int getEpicId () {
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


