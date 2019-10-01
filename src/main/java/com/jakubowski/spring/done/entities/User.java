package com.jakubowski.spring.done.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 15)
    @NonNull private String username;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    @NonNull private String email;

    @NotBlank
    @Size(max = 100)
    @NonNull private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private List<TodoList> todolists = new ArrayList<>();

    @OneToOne
    private UserProperties userProperties;

    @OneToOne
    private UserStatistics userStatistics;

    @JsonIgnore
    private final LocalDate creationDate = LocalDate.now();
}
