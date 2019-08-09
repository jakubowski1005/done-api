package com.jakubowski.spring.done.tasklists;

import java.util.List;

public class TaskList {

    private Long id;
    private String listName;
    private double progress;
    private String color;
    private List<Task> tasks;

    protected TaskList() {}

    public TaskList(Long id, String listName, double progress, String color, List<Task> tasks) {
        this.id = id;
        this.listName = listName;
        this.progress = progress;
        this.color = color;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "id=" + id +
                ", listName='" + listName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskList taskList = (TaskList) o;

        return id.equals(taskList.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
