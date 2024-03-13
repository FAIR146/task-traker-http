package com.example.tasktrackerhttp.controller;

import com.example.tasktrackerhttp.controller.core.put.*;
import com.example.tasktrackerhttp.controller.core.response.*;
import com.example.tasktrackerhttp.dto.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ManagerImplControllerTest extends BaseAuthenticatedControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


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
                .andExpect(jsonPath("$.description").value(putTaskRequest.getDescription()))
                .andExpect(jsonPath("$.userName").exists());
    }

    @Test
    public void putAndGetEpic () throws Exception {
        PutEpicRequest putEpicRequest = new PutEpicRequest();
        putEpicRequest.setName("ff");
        putEpicRequest.setUserName("sdf");
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
                .andExpect(jsonPath("$.description").value(putEpicRequest.getDescription()))
                .andExpect(jsonPath("$.userName").exists());
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
                .andExpect(jsonPath("$.status").value(putSubTaskRequest.getStatus().name())); // добавил name (было expect NEW actual NEW
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
    @Test
    public void DeleteEpicById () throws  Exception {
        PutEpicRequest putEpicRequest = new PutEpicRequest();
        putEpicRequest.setName("fdg");
        putEpicRequest.setDescription("saaa");

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
                delete("/deleteEpicById")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk());

        mockMvc.perform(
                get("/getEpicById")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isNotFound());

    }
    @Test
    public void DeleteSubTaskById () throws Exception {
        PutEpicRequest putEpicRequest = new PutEpicRequest();
        putEpicRequest.setDescription("ii");
        putEpicRequest.setName("tyy");

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
        putSubTaskRequest.setName("AA");
        putSubTaskRequest.setDescription("kk");
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

        long idResponseSubTask = objectMapper.readValue(responseSubTask, PutSubTaskResponse.class).getId();

        mockMvc.perform(
                get("/getSubTaskById")
                        .param("id", String.valueOf(idResponseSubTask)))
                        .andExpect(status().isOk());

        mockMvc.perform(
                        delete("/deleteSubTaskById")
                                .param("id", String.valueOf(idResponseSubTask))) // делит должен работать айди одинак но ничего не происход
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/getSubTaskById")
                                .param("id", String.valueOf(idResponseSubTask)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateTask () throws Exception {
        PutTaskRequest putTaskRequest = new PutTaskRequest();
        putTaskRequest.setName("aa");
        putTaskRequest.setDescription("yy");
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


        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setName("hh");
        updateTaskRequest.setStatus(Status.IN_PROGRESS);
        updateTaskRequest.setDescription("tt");
        updateTaskRequest.setId(id);

        mockMvc.perform(
                patch("/updateTask")
                        .content(objectMapper.writeValueAsString(updateTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());


        mockMvc.perform(
                get("/getTaskById")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updateTaskRequest.getName()))
                .andExpect(jsonPath("$.description").value(updateTaskRequest.getDescription()))
                .andExpect(jsonPath("$.status").value(updateTaskRequest.getStatus().name()))
                .andExpect(jsonPath("$.id").value(updateTaskRequest.getId()));
    }

    @Test
    public void updateEpic () throws Exception {
        PutEpicRequest putEpicRequest = new PutEpicRequest();
        putEpicRequest.setName("aa");
        putEpicRequest.setDescription("daw");

        String response = mockMvc.perform(
                        put("/putEpic")
                                .content(objectMapper.writeValueAsString(putEpicRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readValue(response, PutEpicResponse.class).getId();

        UpdateEpicRequest updateEpicRequest = new UpdateEpicRequest();
        updateEpicRequest.setName("aa");
        updateEpicRequest.setId(id);
        updateEpicRequest.setDescription("ahh");

        mockMvc.perform(
                patch("/updateEpic")
                        .content(objectMapper.writeValueAsString(updateEpicRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        mockMvc.perform(
                get("/getEpicById")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updateEpicRequest.getName()))
                .andExpect(jsonPath("$.description").value(updateEpicRequest.getDescription()))
                .andExpect(jsonPath("$.id").value(updateEpicRequest.getId()));

    }

    @Test
    public void updateSubTask () throws Exception {
        PutEpicRequest putEpicRequest = new PutEpicRequest();
        putEpicRequest.setDescription("ii");
        putEpicRequest.setName("tyy");

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
        putSubTaskRequest.setDescription("pp");
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

        UpdateSubTaskRequest updateSubTaskRequest = new UpdateSubTaskRequest();
        updateSubTaskRequest.setDescription("kk");
        updateSubTaskRequest.setName("uj");
        updateSubTaskRequest.setStatus(Status.NEW);
        updateSubTaskRequest.setId(idSubTask);

        mockMvc.perform(
                        patch("/updateSubTask")
                                .content(objectMapper.writeValueAsString(updateSubTaskRequest)) // не понятно
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        mockMvc.perform(
                        get("/getSubTaskById")
                                .param("id", String.valueOf(idSubTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updateSubTaskRequest.getName()))
                .andExpect(jsonPath("$.description").value(updateSubTaskRequest.getDescription()))
                .andExpect(jsonPath("$.status").value(updateSubTaskRequest.getStatus().name()))
                .andExpect(jsonPath("$.id").value(updateSubTaskRequest.getId()));
    }

}
