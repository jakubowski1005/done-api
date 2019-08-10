package com.jakubowski.spring.done.user.services;

import com.jakubowski.spring.done.user.entities.User;
import com.jakubowski.spring.done.user.entities.UserProperties;
import com.jakubowski.spring.done.user.repositories.UserPropertiesRepository;
import com.jakubowski.spring.done.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPropertiesService {

    private final UserPropertiesRepository userPropertiesRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserPropertiesService(UserRepository userRepository, UserPropertiesRepository userPropertiesRepository) {
        this.userPropertiesRepository = userPropertiesRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserProperties> updateUserProperties(long userId, UserProperties userProperties) {

        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        long userPropertiesId = user.get().getUserProperties().getId();
        System.out.println(userPropertiesId);


//        if(user.get().getUserProperties() == null) {
//            return createUserProperties(userId, userProperties);
//        }

//        user.get().setUserProperties(userProperties);
        userPropertiesRepository.deleteById(userPropertiesId);
        userPropertiesRepository.save(userProperties);
//        userPropertiesRepository.s
        return new ResponseEntity<>(userProperties, HttpStatus.OK);
    }

//    public ResponseEntity<UserProperties> createUserProperties(long userId, UserProperties userProperties) {
//
//        if(userPropertiesRepository.findById(userProperties.getId()).isPresent()) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//
//        UserProperties createdUserProperties = userPropertiesRepository.save(userProperties);
//        userRepository.findById(userId).
//
//        URI uri = ServletUriComponentsBuilder
//                    .fromCurrentRequest()
//                    .path("/{id}")
//                    .buildAndExpand(createdUserProperties.getId())
//                    .toUri();
//
//        return ResponseEntity.created(uri).build();
//    }
}
