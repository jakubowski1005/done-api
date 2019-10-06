package com.jakubowski.spring.done.controllers;

import com.jakubowski.spring.done.payloads.SignInRequest;
import com.jakubowski.spring.done.payloads.SignUpRequest;
import com.jakubowski.spring.done.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String hello() {
        return "Hello world!";
    }

    @GetMapping("/error")
    public String error() {
        return "ERROR";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {
        return authService.authenticateUser(signInRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
}
