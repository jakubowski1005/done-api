package com.jakubowski.spring.done.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowski.spring.done.enums.Color;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todolists")
@Data
@NoArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String listname;

    @NotNull
    private Color color;

    @OneToMany
    private List<Todo> todos = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private User user;


    public void addTodo(Todo todo) {

        todo.setTodoList(this);
        this.todos.add(todo);
    }

    public void delete(Todo todo) {
        this.todos.remove(todo);
    }

}
