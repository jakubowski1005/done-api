package com.jakubowski.spring.done.security;

import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.exceptions.ResourcesNotFoundException;
import com.jakubowski.spring.done.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserSecurity {


    @Autowired
    UserRepository userRepository;

    public boolean isUserAuthorized(Authentication authentication, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourcesNotFoundException("User", "ID", userId)
        );

        log.debug("Authentication: ' {} ', credentials: ' {} ', principal: ' {} ', details: ' {} ' ", authentication.toString(), authentication.getCredentials().toString(),
                authentication.getPrincipal().toString(), authentication.getDetails());

        return true;
        //return userRepository.findById(userId).isPresent();
    }
}
