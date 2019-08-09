package com.jakubowski.spring.done.user;

import com.jakubowski.spring.done.tasklists.TaskList;

import java.util.Collections;
import java.util.List;

public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private List<TaskList> lists = Collections.emptyList();
    private UserProperties userProperties = null;
    private UserStatistics userStatistics = null;

    protected User() {}

    public User(Long id, String username, String email, String password,
                List<TaskList> lists, UserProperties userProperties, UserStatistics userStatistics) {
        this.id = id;
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

    public void setId(Long id) {
        this.id = id;
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

    public void setLists(List<TaskList> lists) {
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
