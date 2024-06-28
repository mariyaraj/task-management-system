
package com.tasks.task_management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.tasks.task_management.model.Priority;
import com.tasks.task_management.model.Task;
import com.tasks.task_management.repository.TaskRepository;
import com.tasks.task_management.service.TaskService;

import java.time.Instant;
import java.util.Optional;

@SpringBootTest
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setDone(false);
        task.setCreated(Instant.now());
        task.setPriority(Priority.NORMAL);
    }

    @Test
    public void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task created = taskService.createTask(task);
        assertNotNull(created);
        assertEquals("Test Task", created.getName());
    }

    @Test
    public void testUpdateTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        task.setDone(true);
        Task updated = taskService.updateTask(1L, task);
        assertTrue(updated.isDone());
    }

    @Test
    public void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(anyLong());
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
