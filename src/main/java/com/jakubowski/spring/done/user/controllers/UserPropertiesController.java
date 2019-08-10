package com.jakubowski.spring.done.user.controllers;

import com.jakubowski.spring.done.user.entities.User;
import com.jakubowski.spring.done.user.entities.UserProperties;
import com.jakubowski.spring.done.user.repositories.UserPropertiesRepository;
import com.jakubowski.spring.done.user.repositories.UserRepository;
import com.jakubowski.spring.done.user.services.UserPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserPropertiesController {

    private final UserPropertiesRepository userPropertiesRepository;
    private final UserPropertiesService userPropertiesService;
    private final UserRepository userRepository;

    @Autowired
    public UserPropertiesController (UserPropertiesRepository userPropertiesRepository,
                                     UserPropertiesService userPropertiesService,
                                     UserRepository userRepository) {

        this.userPropertiesRepository = userPropertiesRepository;
        this.userPropertiesService = userPropertiesService;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/v1/jpa/users/{userId}/properties")
    public UserProperties getUserProperties(@PathVariable long userId) {
        if(userRepository.findById(userId).isPresent()){
            return userRepository.findById(userId).get().getUserProperties();
        }
        return null;
    }

//    @PostMapping("/api/v1/jpa/users/{userId}/properties")
//    public ResponseEntity<UserProperties> createUserProperties(@PathVariable long userId, @RequestBody UserProperties userProperties) {
//        return userPropertiesService.createUserProperties(userId, userProperties);
//    }

    @PutMapping("/api/v1/jpa/users/{userId}/properties")
    public ResponseEntity<UserProperties> updateUserProperties(@PathVariable long userId, @RequestBody UserProperties userProperties) {
        return userPropertiesService.updateUserProperties(userId, userProperties);
    }

    @DeleteMapping("/api/v1/jpa/users/{userId}/properties")
        public ResponseEntity<Void> deleteUserProperties(@PathVariable long userId) {

            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()) {
                long userPropertiesId = user.get().getUserProperties().getId() ;
                userPropertiesRepository.deleteById(userPropertiesId);
                return ResponseEntity.noContent().build();
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
