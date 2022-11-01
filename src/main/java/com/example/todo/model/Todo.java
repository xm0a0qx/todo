package com.example.todo.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 10, max = 200, message
            = "Task description must be between 10 and 200 characters")
    private String task;
    private Boolean isDone;

    public Todo() {
    }

    public Todo(String task, Boolean isDone) {
        this.task = task;
        this.isDone = isDone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}
