package com.tasks.task_management;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import com.tasks.task_management.model.Priority;
import com.tasks.task_management.model.Task;
import com.tasks.task_management.repository.TaskRepository;
import com.tasks.task_management.service.TaskService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TaskControllerTestMock {

	@Autowired
	WebTestClient webTestClient;

	@Autowired
	TaskService taskService;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testCreateTask() throws Exception {
		String taskJson = "{\"name\":\"Test Task\",\"done\":false,\"priority\":\"NORMAL\"}";

		mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(taskJson))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Test Task"));

	}

	@Test
	public void testUpdateTask() throws Exception {

		Task t1 = new Task("Task A", false, Instant.now(), Priority.NORMAL);
		taskRepository.save(t1);

		String taskJson = "{\"name\":\"Updated Task\",\"done\":true,\"priority\":\"URGENT\"}";

		mockMvc.perform(put("/api/tasks/1").contentType(MediaType.APPLICATION_JSON).content(taskJson))
				.andExpect(status().isOk()).andExpect(jsonPath("$.done").value(true));
	}

	@Test
	public void testDeleteTask() throws Exception {
		mockMvc.perform(delete("/api/tasks/1")).andExpect(status().isNoContent());
	}
}
