package com.jakubowski.spring.done.security;

import org.springframework.security.core.Authentication;

public class UserSecurity {

    public boolean isUserAuthorized(Authentication authentication, Long userId) {
        JwtUserPrincipal principal = (JwtUserPrincipal) authentication.getPrincipal();
        return principal.getId().equals(userId);
    }
}
