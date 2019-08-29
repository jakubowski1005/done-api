package com.jakubowski.spring.done.entities;

import com.jakubowski.spring.done.enums.Priority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    private Boolean isDone;

    @NotBlank
    private Priority priority;

    public Todo() {
    }

    public Todo(@NotBlank @Size(max = 30) String description, @NotBlank Boolean isDone, @NotBlank Priority priority) {
        this.description = description;
        this.isDone = isDone;
        this.priority = priority;
    }

    public Long getId() {
        return id;
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

    public void setDone(Boolean done) {
        isDone = done;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
