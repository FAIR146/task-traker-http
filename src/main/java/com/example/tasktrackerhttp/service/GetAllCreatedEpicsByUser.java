package com.example.tasktrackerhttp.service;

import com.example.tasktrackerhttp.dto.Epic;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class GetAllCreatedEpicsByUser {
    private List<Epic> inProgressEpic;
    private List<Epic> newEpic;
    private List<Epic> doneEpic;
}
