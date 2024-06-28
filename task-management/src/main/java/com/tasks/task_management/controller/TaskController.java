package com.tasks.task_management.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.task_management.model.Task;
import com.tasks.task_management.service.TaskService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskService taskService;

	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build(); // Status code 204 No Content
	}

	@GetMapping("/{id}")
	public Task getTaskById(@PathVariable("id") Long id) {
		return taskService.findById(id).get();
	}

	@GetMapping
	ResponseEntity<List<Task>> getAllTasks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Pageable pageable = PageRequest.of(page, size);
		List<Task> tasks = taskService.getAllTasks(pageable);
		return ResponseEntity.ok().body(tasks);

	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping()
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		task.setCreated(Instant.now()); // Set the created date to now
		Task savedTask = taskService.createTask(task);
		return ResponseEntity.status(201).body(savedTask); // Status code 201 Created
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {

		Task t = taskService.findById(id).map(task -> {
			task.setName(updatedTask.getName());
			task.setPriority(updatedTask.getPriority());
			task.setDone(updatedTask.isDone());
			return taskService.updateTask(id, task);
		}).orElseThrow(() -> new RuntimeException("Task not found with id " + id));

		return ResponseEntity.status(200).body(t); // Status code 200 OK
	}

}
