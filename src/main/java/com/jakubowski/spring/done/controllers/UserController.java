package com.jakubowski.spring.done.controllers;

import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.entities.UserProperties;
import com.jakubowski.spring.done.entities.UserStatistics;
import com.jakubowski.spring.done.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/users")
    public User getAllUsers(@RequestParam(value = "username") String usernameOrEmail) {
        return userService.getUserByUsernameOrEmail(usernameOrEmail);
    }

    @GetMapping("/users/{userId}/properties")
    public UserProperties getUserProperties(@PathVariable long userId) {
        return userService.getUserProperties(userId);
    }

    @GetMapping("/users/{userId}/statistics")
    public UserStatistics getUserStatistics(@PathVariable long userId) {
        return userService.getUserStatistics(userId);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/users/{userId}/properties")
    @Transactional
    public ResponseEntity<?> updateUserProperties(@PathVariable long userId, @RequestBody UserProperties userProperties) {
        return userService.updateUserProperties(userId, userProperties);
    }

    @PutMapping("/users/{userId}/statistics")
    @Transactional
    public ResponseEntity<?> updateUserStatistics(@PathVariable long userId, @RequestBody UserStatistics userStatistics) {
        return userService.updateUserStatistics(userId, userStatistics);
    }
}
