package com.jakubowski.spring.done.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowski.spring.done.enums.Priority;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "todos")
@Data
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 30)
    private String description;

    @NotNull
    private boolean isDone;

    @NotNull
    private Priority priority;

    @ManyToOne
    @JsonIgnore
    private TodoList todoList;
}
