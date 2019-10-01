package com.jakubowski.spring.done.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_statistics")
@Data
@NoArgsConstructor
public class UserStatistics {

    @Id
    @GeneratedValue
    private Long id;
    private int completedTasks;
    private int completedLists;
    private int daysWithApp;
    private int activeLists;
}
