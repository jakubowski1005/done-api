package com.jakubowski.spring.done.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_statistics")
public class UserStatistics {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Integer completedTasks;

    public UserStatistics() {
    }

    public UserStatistics(@NotBlank Integer completedTasks) {
        this.completedTasks = completedTasks;
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
}
