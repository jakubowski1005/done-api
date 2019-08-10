package com.jakubowski.spring.done.user.controllers;

import com.jakubowski.spring.done.user.entities.User;
import com.jakubowski.spring.done.user.repositories.UserRepository;
import com.jakubowski.spring.done.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/api/v1/jpa/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/v1/jpa/users/{id}")
    public User getUserById(@PathVariable long id) {
        return userRepository.findById(id).get();
    }

    @DeleteMapping("/api/v1/jpa/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable long id) {
       userRepository.deleteById(id);
       return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/v1/jpa/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/api/v1/jpa/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}

