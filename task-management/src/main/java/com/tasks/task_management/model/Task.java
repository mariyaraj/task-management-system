package com.tasks.task_management.model;


import java.time.Instant;
import java.util.Objects;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Task {


	@Id
    @GeneratedValue(generator = "Incremental")
    @Column(name = "id", updatable = false, nullable = false, insertable = false, unique = true)
	private Long id;

	private String name;

    private boolean done;

    private Instant created;

    private Priority priority;
    
    public Task() {
 	}
    
    public Task(String name, boolean done, Instant now, Priority priority) {
		this.name=name;
		this.done=done;
		this.created=now;
		this.priority= priority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public int hashCode() {
		return Objects.hash(created, done, id, name, priority);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return done == other.done && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && priority == other.priority;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", done=" + done + ", created=" + created + ", priority="
				+ priority + "]";
	}

	
    
    
	
}