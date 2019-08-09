package com.jakubowski.spring.done.tasklists;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TaskList {

    @Id
    @GeneratedValue
    private Long id;
    private String listName;
    private double progress;
    private String color;
    private ArrayList<Task> tasks;

    protected TaskList() {}

    public TaskList(String listName, double progress, String color, ArrayList<Task> tasks) {
        this.listName = listName;
        this.progress = progress;
        this.color = color;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
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

    public void setTasks(ArrayList<Task> tasks) {
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
