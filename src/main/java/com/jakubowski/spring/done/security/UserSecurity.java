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

        String principalUsername = JwtUserPrincipal.create(user).getUsername();
        String userUsername = (String) authentication.getPrincipal();

        //return userUsername.equals(principalUsername);
        return userRepository.findById(userId).isPresent();
    }
}
