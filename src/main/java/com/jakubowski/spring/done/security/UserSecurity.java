package com.jakubowski.spring.done.security;

import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.exceptions.ResourcesNotFoundException;
import com.jakubowski.spring.done.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    @Autowired
    UserRepository userRepository;

    public boolean isUserAuthorized(Authentication authentication, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourcesNotFoundException("User", "ID", userId)
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String principalUsername = userDetails.getUsername();

        return user.getUsername().equals(principalUsername);
        //return userRepository.findById(userId).isPresent();
    }
}
