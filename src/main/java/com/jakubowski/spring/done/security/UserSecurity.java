package com.jakubowski.spring.done.security;

import com.jakubowski.spring.done.entities.User;
import org.springframework.security.core.Authentication;

public class UserSecurity {

    public boolean isUserAuthorized(Authentication authentication, Long userId) {
        User loggedUser = (User) authentication.getPrincipal();
        return loggedUser.getId().equals(userId);
    }
}
