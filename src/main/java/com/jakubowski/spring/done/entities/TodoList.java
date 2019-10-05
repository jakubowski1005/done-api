package com.jakubowski.spring.done.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakubowski.spring.done.enums.Color;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todolists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    @NonNull private String listname;

    @NonNull private Color color;

    @OneToMany
    private List<Todo> todos = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private User user;
}
