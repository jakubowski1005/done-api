package com.jakubowski.spring.done.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowski.spring.done.enums.Color;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todolists")
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

    @NotNull
    private double progress;


    public TodoList() {
    }

    public TodoList(String listname, Color color) {
        this.listname = listname;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getListname() {
        return listname;
    }

    public Color getColor() {
        return color;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<Todo> todos) {
        this.todos = todos;
    }

    public void addTodo(Todo todo) {

        todo.setTodoList(this);
        this.todos.add(todo);
    }

    public void delete(Todo todo) {
        this.todos.remove(todo);
    }

    public void deleteById(int id) {
        this.todos.remove(id);
    }

    public void updateTodo(int id, Todo newTodo) {
        this.todos.remove(id);
        this.todos.add(newTodo);
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
