package com.jakubowski.spring.done.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_statistics")
public class UserStatistics {

    @Id
    @GeneratedValue
    private Long id;
    private int completedTasks;
    private int completedLists;
    private int daysWithApp;
    private int activeLists;

    public UserStatistics() {
    }

    public UserStatistics(int completedTasks, int completedLists, int daysWithApp, int activeLists) {
        this.completedTasks = completedTasks;
        this.completedLists = completedLists;
        this.daysWithApp = daysWithApp;
        this.activeLists = activeLists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(Integer completedTasks) {
        this.completedTasks = completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getCompletedLists() {
        return completedLists;
    }

    public void setCompletedLists(int completedLists) {
        this.completedLists = completedLists;
    }

    public int getDaysWithApp() {
        return daysWithApp;
    }

    public void setDaysWithApp(int daysWithApp) {
        this.daysWithApp = daysWithApp;
    }

    public int getActiveLists() {
        return activeLists;
    }

    public void setActiveLists(int activeLists) {
        this.activeLists = activeLists;
    }
}
