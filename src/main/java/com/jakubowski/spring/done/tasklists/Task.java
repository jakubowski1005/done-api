package com.jakubowski.spring.done.tasklists;

public class Task {

    private Long id;
    private String description;
    private String priority;
    private boolean isDone;

    protected Task() {}

    public Task(Long id, String description, String priority, boolean isDone) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.isDone = isDone;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
