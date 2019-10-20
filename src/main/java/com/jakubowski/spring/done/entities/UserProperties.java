package com.jakubowski.spring.done.entities;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @NonNull
    private String name;

    @Size(max = 30)
    @NonNull
    private String lastName;

    @Size(max = 10)
    @NonNull
    private String gender;

    @Size(max = 10)
    @NonNull
    private String nationality;

    @URL
    private String avatarURL;
}
