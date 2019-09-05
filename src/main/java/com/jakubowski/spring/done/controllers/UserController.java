package com.jakubowski.spring.done.controllers;

import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.entities.UserProperties;
import com.jakubowski.spring.done.entities.UserStatistics;
import com.jakubowski.spring.done.repositories.UserPropertiesRepository;
import com.jakubowski.spring.done.repositories.UserRepository;
import com.jakubowski.spring.done.repositories.UserStatisticsRepository;
import com.jakubowski.spring.done.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPropertiesRepository userPropertiesRepository;

    @Autowired
    private UserStatisticsRepository userStatisticsRepository;

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable long userId, @RequestHeader(value = "Authorization") String authorizationHeader) {

        if(!jwtProvider.isUserAuthorized(userId, authorizationHeader)) return null;

        return userRepository.findById(userId).get();
    }

    @GetMapping("/users/{userId}/properties")
    public UserProperties getUserProperties(@PathVariable long userId,
                                            @RequestHeader(value = "Authorization") String authorizationHeader) {

        if(!jwtProvider.isUserAuthorized(userId, authorizationHeader)) {
           return null;
        }

        return userRepository.findById(userId).get().getUserProperties();
    }

    @GetMapping("/users/{userId}/statistics")
    public UserStatistics getUserStatistics(@PathVariable long userId,
                                            @RequestHeader(value = "Authorization") String authorizationHeader) {

        if(!jwtProvider.isUserAuthorized(userId, authorizationHeader)) {
            return null;
        }

        return userRepository.findById(userId).get().getUserStatistics();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId,
                                        @RequestHeader(value = "Authorization") String authorizationHeader) {

        if(!jwtProvider.isUserAuthorized(userId, authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{userId}/properties")
    @Transactional
    public ResponseEntity<?> updateUserProperties(@PathVariable long userId, @RequestBody UserProperties userProperties,
                                                  @RequestHeader(value = "Authorization") String authorizationHeader) {

        if(!jwtProvider.isUserAuthorized(userId, authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId).get();

        if(user.getUserProperties() == null) {
            user.setUserProperties(userProperties);
            userPropertiesRepository.save(userProperties);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        userPropertiesRepository.delete(user.getUserProperties());
        user.setUserProperties(userProperties);
        userPropertiesRepository.save(userProperties);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/statistics")
    @Transactional
    public ResponseEntity<?> updateUserStatistics(@PathVariable long userId, @RequestBody UserStatistics userStatistics,
                                                  @RequestHeader(value = "Authorization") String authorizationHeader) {

        if(!jwtProvider.isUserAuthorized(userId, authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId).get();

        if(user.getUserStatistics() == null) {
            user.setUserStatistics(userStatistics);
            userStatisticsRepository.save(userStatistics);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        userStatisticsRepository.delete(user.getUserStatistics());
        user.setUserStatistics(userStatistics);
        userStatisticsRepository.save(userStatistics);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
