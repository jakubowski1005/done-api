package com.jakubowski.spring.done.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    public boolean isUserAuthorized(Authentication authentication, Long userId) {
        System.out.println("Authentication");
        System.out.println(authentication.toString());
        System.out.println("getPrincipal()");
        System.out.println(authentication.getPrincipal().toString());
        System.out.println("getCredentials()");
        System.out.println(authentication.getCredentials().toString());
        System.out.println("getDetails()");
        System.out.println(authentication.getDetails().toString());
        System.out.println("getAuthorities");
        System.out.println(authentication.getAuthorities().toString());

        //JwtUserPrincipal principal = (JwtUserPrincipal) authentication.getPrincipal();
        //return principal.getId().equals(userId);
        return true;
    }
}
