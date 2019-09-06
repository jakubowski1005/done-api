package com.jakubowski.spring.done.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowski.spring.done.enums.Priority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 30)
    private String description;

    @NotNull
    private boolean isDone;

    @NotNull
    private Priority priority;

    @ManyToOne
    @JsonIgnore
    private TodoList todoList;

    public Todo() {
    }

    public Todo(String description, Boolean isDone, Priority priority) {
        this.description = description;
        this.isDone = isDone;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getDone() {
        return isDone;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return this.isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }
}
