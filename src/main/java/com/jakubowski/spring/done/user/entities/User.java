package com.jakubowski.spring.done.user.entities;

import com.jakubowski.spring.done.tasklists.TaskList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;
//    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "user")
//    private ArrayList<TaskList> lists;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private UserProperties userProperties;
//    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "user")
//    private UserStatistics userStatistics;

    protected User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
//        this.lists = lists;
        this.userProperties = new UserProperties("", "", false, "");
//        this.userStatistics = userStatistics;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public UserProperties getUserProperties() {
        return userProperties;
    }

    public void setUserProperties(UserProperties userProperties) {
        this.userProperties = userProperties;
    }



    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
