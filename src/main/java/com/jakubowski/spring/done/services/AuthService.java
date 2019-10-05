package com.jakubowski.spring.done.services;

import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.payloads.ApiResponse;
import com.jakubowski.spring.done.payloads.JwtAuthenticationResponse;
import com.jakubowski.spring.done.payloads.SignInRequest;
import com.jakubowski.spring.done.payloads.SignUpRequest;
import com.jakubowski.spring.done.repositories.UserRepository;
import com.jakubowski.spring.done.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public ResponseEntity<?> authenticateUser(SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsernameOrEmail(),
                signInRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateTokenFromAuthentication(authentication);

        if(!jwtProvider.validateToken(token)) {
            return new ResponseEntity<>(new ApiResponse(false, "User doesn't exist"), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new JwtAuthenticationResponse(token), HttpStatus.OK);
    }

    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "E-mail already in use!"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username already taken!"), HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encodedPassword);
        userRepository.save(user);

        logger.info("Created new user with username: '{}'", user.getUsername());
        return new ResponseEntity<>(new ApiResponse(true, "User registered successfully!"), HttpStatus.CREATED);
    }
}
