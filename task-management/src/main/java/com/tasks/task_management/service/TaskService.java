package com.tasks.task_management.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tasks.task_management.exception.TaskNotFoundException;
import com.tasks.task_management.model.Task;
import com.tasks.task_management.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TaskService {

	private final TaskRepository repository;

	public TaskService(TaskRepository repository) {
		this.repository = repository;
	}

	public Task createTask(Task task) {
		task.setCreated(Instant.now());
		return repository.save(task);
	}

	public Task updateTask(Long id, Task updatedTask) {

		Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
		task.setName(updatedTask.getName());
		task.setDone(updatedTask.isDone());
		task.setPriority(updatedTask.getPriority());
		return repository.save(task);
	}

	public void deleteTask(Long id) {
		repository.deleteById(id);
	}

	public List<Task> getAllTasks() {
		return repository.findAll();
	}

	public List<Task> getAllTasks(Pageable pageable) {

		Page<Task> page = repository.findAll(pageable);
		return page.getContent();
	}

	public Optional<Task> findById(Long id) {
		return repository.findById(id);
	}
}
