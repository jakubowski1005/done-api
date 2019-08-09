package com.jakubowski.spring.done.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserStatistics {

    @Id
    @GeneratedValue
    private Long id;
    private int completedLists;
    private int completedTasks;
    private int daysWithDone;
    private int activeLists;

    protected UserStatistics() {}

    public UserStatistics(int completedLists, int completedTasks, int daysWithDone, int activeLists) {
        this.completedLists = completedLists;
        this.completedTasks = completedTasks;
        this.daysWithDone = daysWithDone;
        this.activeLists = activeLists;
    }

    public int getCompletedLists() {
        return completedLists;
    }

    public void setCompletedLists(int completedLists) {
        this.completedLists = completedLists;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getDaysWithDone() {
        return daysWithDone;
    }

    public void setDaysWithDone(int daysWithDone) {
        this.daysWithDone = daysWithDone;
    }

    public int getActiveLists() {
        return activeLists;
    }

    public void setActiveLists(int activeLists) {
        this.activeLists = activeLists;
    }
}
