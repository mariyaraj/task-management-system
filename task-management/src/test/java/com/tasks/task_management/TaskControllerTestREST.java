package com.tasks.task_management;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;


import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.tasks.task_management.model.Priority;
import com.tasks.task_management.model.Task;
import com.tasks.task_management.repository.TaskRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTestREST {

	@LocalServerPort
	private Integer port;

	@Autowired
	TaskRepository taskRepository;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost:" + port;
		taskRepository.deleteAll();

		Task t1 = new Task("Task A", false, Instant.now(), Priority.URGENT);
		Task t2 = new Task("Task B", false, Instant.now(), Priority.URGENT);
		Task t3 = new Task("Task C", false, Instant.now(), Priority.URGENT);
		Task t4 = new Task("Task D", false, Instant.now(), Priority.URGENT);

		taskRepository.saveAll(List.of(t1, t2, t3, t4));
		System.out.print("Task Repository size IS:" + taskRepository.findAll().size());
	}

	@Test
	void testFindAll() {
		given().contentType(ContentType.JSON).when().get("/api/tasks").then().statusCode(200)
				.contentType(ContentType.JSON).body("$", hasSize(4));
	}

	@Test
	public void testDeleteById() {
		Task task = taskRepository.save(new Task("Task E", false, Instant.now(), Priority.NORMAL));
		Long id = task.getId();

		given().pathParam("id", id).when().delete("/api/tasks/{id}").then().statusCode(204);

		// Verify the task is deleted
		boolean exists = taskRepository.existsById(id);
		assertFalse(exists);
	}

	@Test
	public void testCreate() {
		Task newTask = new Task("Task F", false, Instant.now(), Priority.NORMAL);

		given().contentType(ContentType.JSON).body(newTask).when().post("/api/tasks").then().statusCode(201) // Status
																												// code
																												// 201
																												// Created
				.contentType(ContentType.JSON).body("name", equalTo("Task F"));

		// Verify the task is created
		List<Task> tasks = taskRepository.findByName("Task F");
		assertEquals(1, tasks.size());

		Task savedTask = tasks.get(0);
		assertEquals("Task F", savedTask.getName());
		assertEquals(Priority.NORMAL, savedTask.getPriority());
	}

}
