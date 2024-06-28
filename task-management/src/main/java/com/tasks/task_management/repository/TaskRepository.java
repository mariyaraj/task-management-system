package com.tasks.task_management.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasks.task_management.model.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByName(String name);

	
}