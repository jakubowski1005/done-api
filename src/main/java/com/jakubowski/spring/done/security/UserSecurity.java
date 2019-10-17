package com.jakubowski.spring.done.security;

import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.exceptions.ResourcesNotFoundException;
import com.jakubowski.spring.done.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {


    @Autowired
    UserRepository userRepository;

    public boolean isUserAuthorized(Authentication authentication, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourcesNotFoundException("User", "ID", userId)
        );

        String current = authentication.getName();
        return current.equals(user.getUsername());
    }

    public boolean isUserAuthorizedToGetUserByUsername(Authentication authentication, String usernameOrEmail) {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail).orElseThrow(
                () -> new ResourcesNotFoundException("User", "username or email", usernameOrEmail)
        );

        String current = authentication.getName();
        return current.equals(user.getUsername());
    }
}
