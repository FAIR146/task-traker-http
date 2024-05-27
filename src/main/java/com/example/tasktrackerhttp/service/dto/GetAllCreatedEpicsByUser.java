package com.example.tasktrackerhttp.service.dto;

import com.example.tasktrackerhttp.dto.Epic;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class GetAllCreatedEpicsByUser {
    private List<Epic> inProgressEpics;
    private List<Epic> newEpics;
    private List<Epic> doneEpics;
}
