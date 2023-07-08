package com.example.tasktrackerhttp;

import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.tasktrackerhttp.controller.response.AddEpicResponse;
import com.example.tasktrackerhttp.controller.response.AddSubTaskResponse;
import com.example.tasktrackerhttp.controller.response.AddTaskResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskTrackerHttpApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void putTask () throws Exception {
		Task task = new Task();
		task.setName("up");
		task.setStatus(Status.NEW);
		task.setDescription("aa");


		String response = mockMvc.perform(
					put("/putTask")
						.content(objectMapper.writeValueAsString(task))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andReturn().getResponse().getContentAsString();

		long id = objectMapper.readValue(response, AddTaskResponse.class).getId();


		mockMvc.perform(
				get("/getTaskById")
						.param("id", String.valueOf(id))
		)
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value(task.getName()))
				.andExpect(jsonPath("$.status").value(task.getStatus().name()))
				.andExpect(jsonPath("$.description").value(task.getDescription()));
	}

	@Test
	public void putEpic () throws Exception {
		Epic epic = new Epic();
		epic.setName("ff");
		epic.setDescription("ssss");

		String response = mockMvc.perform(
				put("/putTask")
						.content(objectMapper.writeValueAsString(epic))
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andReturn().getResponse().getContentAsString();

		long id = objectMapper.readValue(response, AddEpicResponse.class).getId();

		mockMvc.perform(
				get("/getEpicById")
						.param("id", String.valueOf(id))
		)
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value(epic.getName()))
				.andExpect(jsonPath("$.description").value(epic.getDescription()));
	}
	@Test
	public void putSubTask () throws Exception {
		Epic epic = new Epic();
		SubTask subTask = new SubTask(epic);
		subTask.setName("ffs");
		subTask.setDescription("ghf");
		subTask.setStatus(Status.NEW);

		String response = mockMvc.perform(
				put("/putSubTask")
						.content(objectMapper.writeValueAsString(subTask))
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andReturn().getResponse().getContentAsString();

		long id = objectMapper.readValue(response, AddSubTaskResponse.class).getId();

		mockMvc.perform(
				get("/getSubTaskById")
						.param("id", String.valueOf(id))
		)
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.description").value(subTask.getDescription()))
				.andExpect(jsonPath("$.name").value(subTask.getName()))
				.andExpect(jsonPath("$.status").value(subTask.getStatus()))
				.andExpect(jsonPath("$.epic").value(epic));
	}
//	@Test
//	public void getTaskById() throws Exception {
//		Task task = new Task();
//		task.setName("dfdg");
//		task.setDescription("hf");
//		task.setStatus(Status.NEW);
//
//		String response = mockMvc.perform(
//				get("/getTaskById")
//						.content(objectMapper.writeValueAsString(task))
//						.contentType(MediaType.APPLICATION_JSON)
//		)
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.id").isNumber())
//				.andReturn().getResponse().getContentAsString();
//
//		long id = objectMapper.readValue(response, GetTaskResponse.class).getId();
//
//		mockMvc.perform(
//				get("/getTaskById/{id]")
//						.
//		)
//	}
//	@Test
//	public void getEpicById() throws Exception {
//		Epic epic = new Epic();
//		epic.setName("dd");
//		epic.setDescription("dds");
//
//		String response = mockMvc.perform(
//				get("/getEpicById/{id}")
//						.content(objectMapper.writeValueAsString(epic))
//						.contentType(MediaType.APPLICATION_JSON)
//		)
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.id").isNumber())
//				.andReturn().getResponse().getContentAsString();
//
//		long id = objectMapper.readValue(response, GetEpicResponse.class).getId();
//		mockMvc.perform(
//				get("/getEpicById/{id}")
//						.
//		)
//	}
//	@Test
//	public void DeleteTaskById () throws Exception {
//		Task task = new Task();
//		String name = "walk";
//		task.setName(name);
//
//		mockMvc.perform(
//				delete("/deleteTaskById/{id}", task.getId())
//				.andExpect(content().json(objectMapper.writeValueAsString(task)));
//				)
//
//	}



}
