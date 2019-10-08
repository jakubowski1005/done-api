package com.jakubowski.spring.done.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    public boolean isUserAuthorized(Authentication authentication, Long userId) {
        //JwtUserPrincipal principal = (JwtUserPrincipal) authentication.getPrincipal();
        //return principal.getId().equals(userId);
        return true;
    }
}
