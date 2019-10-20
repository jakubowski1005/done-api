package com.jakubowski.spring.done.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserStatistics {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private int completedTasks;

    @NonNull
    private int completedLists;

    @NonNull
    private int daysWithApp;

    @NonNull
    private int activeLists;
}
