package com.jakubowski.spring.done.services;

import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.entities.UserProperties;
import com.jakubowski.spring.done.entities.UserStatistics;
import com.jakubowski.spring.done.repositories.UserPropertiesRepository;
import com.jakubowski.spring.done.repositories.UserRepository;
import com.jakubowski.spring.done.repositories.UserStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPropertiesRepository userPropertiesRepository;

    @Autowired
    private UserStatisticsRepository userStatisticsRepository;


    public User getUserById(long userId) {
        return userRepository.findById(userId).get();
    }

    public UserProperties getUserProperties(long userId) {
        return userRepository.findById(userId).get().getUserProperties();
    }

    public UserStatistics getUserStatistics(long userId) {
        return userRepository.findById(userId).get().getUserStatistics();
    }

    public ResponseEntity<?> deleteUser(long userId) {
        userRepository.deleteById(userId);
        logger.info("User with ID '{}' has been deleted.", userId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> updateUserProperties(long userId, UserProperties userProperties) {

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

    public ResponseEntity<?> updateUserStatistics(long userId, UserStatistics userStatistics) {

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
