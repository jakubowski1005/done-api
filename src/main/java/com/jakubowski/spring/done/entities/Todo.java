package com.jakubowski.spring.done.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowski.spring.done.enums.Priority;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "todos")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Todo {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 30)
    @NonNull private String description;

    @NonNull
    private boolean isDone;

    @NonNull
    private Priority priority;

    @ManyToOne
    @JsonIgnore
    private TodoList todoList;
}
