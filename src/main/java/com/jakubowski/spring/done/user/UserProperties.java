package com.jakubowski.spring.done.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserProperties {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private boolean isMale;
    private String avatarPath;

    protected UserProperties() {}

    public UserProperties(String firstName, String lastName, boolean isMale, String avatarPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isMale = isMale;
        this.avatarPath = avatarPath;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
