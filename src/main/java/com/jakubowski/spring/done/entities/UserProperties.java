package com.jakubowski.spring.done.entities;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_properties")
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


    public UserProperties() {
    }

    public UserProperties(@Size(max = 20) String name, @Size(max = 30) String lastName, @URL String avatarURL) {
        this.name = name;
        this.lastName = lastName;
        this.avatarURL = avatarURL;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
