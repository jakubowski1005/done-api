package com.jakubowski.spring.done.user;

import com.jakubowski.spring.done.tasklists.TaskList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
    private ArrayList<TaskList> lists = null;
    @OneToOne
    private UserProperties userProperties = null;
    @OneToOne
    private UserStatistics userStatistics = null;

    protected User() {}

    public User(String username, String email, String password,
                ArrayList<TaskList> lists, UserProperties userProperties, UserStatistics userStatistics) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.lists = lists;
        this.userProperties = userProperties;
        this.userStatistics = userStatistics;
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

    public List<TaskList> getLists() {
        return lists;
    }

    public void setLists(ArrayList<TaskList> lists) {
        this.lists = lists;
    }

    public UserProperties getUserProperties() {
        return userProperties;
    }

    public void setUserProperties(UserProperties userProperties) {
        this.userProperties = userProperties;
    }

    public UserStatistics getUserStatistics() {
        return userStatistics;
    }

    public void setUserStatistics(UserStatistics userStatistics) {
        this.userStatistics = userStatistics;
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
