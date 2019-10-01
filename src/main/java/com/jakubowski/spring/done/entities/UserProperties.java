package com.jakubowski.spring.done.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_properties")
@Data
@NoArgsConstructor
public class UserProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    private String name;

    @Size(max = 30)
    private String lastName;

    @URL
    private String avatarURL;
}
