package com.example.tasktrackerhttp.controller;

import com.example.tasktrackerhttp.controller.request.put.PutEpicRequest;
import com.example.tasktrackerhttp.controller.request.put.PutSubTaskRequest;
import com.example.tasktrackerhttp.controller.request.put.PutTaskRequest;
import com.example.tasktrackerhttp.controller.response.*;
import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ManagerControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void putAndGetTask () throws Exception {
        PutTaskRequest putTaskRequest = new PutTaskRequest();
        putTaskRequest.setName("ff");
        putTaskRequest.setDescription("aa");
        putTaskRequest.setStatus(Status.NEW);


        String response = mockMvc.perform(
                        put("/putTask")
                                .content(objectMapper.writeValueAsString(putTaskRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readValue(response, PutTaskResponse.class).getId();


        mockMvc.perform(
                        get("/getTaskById")
                                .param("id", String.valueOf(id))
                )
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(putTaskRequest.getName()))
                .andExpect(jsonPath("$.status").value(putTaskRequest.getStatus().name()))
                .andExpect(jsonPath("$.description").value(putTaskRequest.getDescription()));
    }

    @Test
    public void putAndGetEpic () throws Exception {
        PutEpicRequest putEpicRequest = new PutEpicRequest();
        putEpicRequest.setName("ff");
        putEpicRequest.setDescription("ss");

        String response = mockMvc.perform(
                        put("/putEpic")
                                .content(objectMapper.writeValueAsString(putEpicRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readValue(response, PutEpicResponse.class).getId();

        mockMvc.perform(
                        get("/getEpicById")
                                .param("id", String.valueOf(id))
                )
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(putEpicRequest.getName()))
                .andExpect(jsonPath("$.description").value(putEpicRequest.getDescription()));
    }
    @Test
    public void putAndGetSubTask () throws Exception {
        PutEpicRequest putEpicRequest = new PutEpicRequest();
        putEpicRequest.setName("jj");
        putEpicRequest.setDescription("ui");
        String responseEpic = mockMvc.perform(
                        put("/putEpic")
                                .content(objectMapper.writeValueAsString(putEpicRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();

        long idEpic = objectMapper.readValue(responseEpic, PutEpicResponse.class).getId();

        PutSubTaskRequest putSubTaskRequest = new PutSubTaskRequest();
        putSubTaskRequest.setName("aa");
        putSubTaskRequest.setDescription("jhj");
        putSubTaskRequest.setStatus(Status.NEW);
        putSubTaskRequest.setEpicId(idEpic);

        String responseSubTask = mockMvc.perform(
                        put("/putSubTask")
                                .content(objectMapper.writeValueAsString(putSubTaskRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();

        long idSubTask = objectMapper.readValue(responseSubTask, PutSubTaskResponse.class).getId();

        mockMvc.perform(
                        get("/getSubTaskById")
                                .param("id", String.valueOf(idSubTask))
                )
                .andExpect(jsonPath("$.id").value(idSubTask))
                .andExpect(jsonPath("$.description").value(putSubTaskRequest.getDescription()))
                .andExpect(jsonPath("$.name").value(putSubTaskRequest.getName()))
                .andExpect(jsonPath("$.status").value(putSubTaskRequest.getStatus()))
                .andExpect(jsonPath("$.epic").value(putEpicRequest));
    }

	@Test
	public void DeleteTaskById () throws Exception {
        PutTaskRequest putTaskRequest = new PutTaskRequest();
        putTaskRequest.setName("ff");
        putTaskRequest.setDescription("aa");
        putTaskRequest.setStatus(Status.NEW);


        String response = mockMvc.perform(
                        put("/putTask")
                                .content(objectMapper.writeValueAsString(putTaskRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readValue(response, PutTaskResponse.class).getId();

        mockMvc.perform(
                delete("/deleteTaskById")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk());

        mockMvc.perform(
                get("/getTaskById")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isNotFound());

	}



}
