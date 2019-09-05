package com.jakubowski.spring.done.controllers;

import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.entities.UserProperties;
import com.jakubowski.spring.done.entities.UserStatistics;
import com.jakubowski.spring.done.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable long userId,
                            @RequestHeader(value = "Authorization") String authorizationHeader) {
        return userService.getUserById(userId, authorizationHeader);
    }

    @GetMapping("/users/{userId}/properties")
    public UserProperties getUserProperties(@PathVariable long userId,
                                            @RequestHeader(value = "Authorization") String authorizationHeader) {
        return userService.getUserProperties(userId, authorizationHeader);
    }

    @GetMapping("/users/{userId}/statistics")
    public UserStatistics getUserStatistics(@PathVariable long userId,
                                            @RequestHeader(value = "Authorization") String authorizationHeader) {
        return userService.getUserStatistics(userId, authorizationHeader);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId,
                                        @RequestHeader(value = "Authorization") String authorizationHeader) {
        return userService.deleteUser(userId, authorizationHeader);
    }

    @PutMapping("/users/{userId}/properties")
    @Transactional
    public ResponseEntity<?> updateUserProperties(@PathVariable long userId, @RequestBody UserProperties userProperties,
                                                  @RequestHeader(value = "Authorization") String authorizationHeader) {
        return userService.updateUserProperties(userId, userProperties, authorizationHeader);
    }

    @PutMapping("/users/{userId}/statistics")
    @Transactional
    public ResponseEntity<?> updateUserStatistics(@PathVariable long userId, @RequestBody UserStatistics userStatistics,
                                                  @RequestHeader(value = "Authorization") String authorizationHeader) {
        return userService.updateUserStatistics(userId, userStatistics, authorizationHeader);
    }
}
