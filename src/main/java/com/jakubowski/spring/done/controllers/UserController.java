package com.jakubowski.spring.done.controllers;

import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.entities.UserProperties;
import com.jakubowski.spring.done.repositories.UserPropertiesRepository;
import com.jakubowski.spring.done.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPropertiesRepository userPropertiesRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {

        if(userRepository.findById(id).isPresent()) return userRepository.findById(id).get();
        return null;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/properties")
    @Transactional
    public ResponseEntity<?> updateUserProperties(@PathVariable long id, @RequestBody UserProperties userProperties) {

        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) return ResponseEntity.noContent().build();

        if(user.get().getUserProperties() == null) {
            user.get().setUserProperties(userProperties);
            userPropertiesRepository.save(userProperties);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        userPropertiesRepository.delete(user.get().getUserProperties());
        user.get().setUserProperties(userProperties);
        userPropertiesRepository.save(userProperties);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
