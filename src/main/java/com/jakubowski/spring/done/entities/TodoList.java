package com.jakubowski.spring.done.entities;

import com.jakubowski.spring.done.enums.Color;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "todolists")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String listname;

    @NotBlank
    private Color color;


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
}
