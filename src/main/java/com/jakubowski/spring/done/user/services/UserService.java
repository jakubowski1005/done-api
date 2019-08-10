package com.jakubowski.spring.done.user.services;

import com.jakubowski.spring.done.user.entities.User;
import com.jakubowski.spring.done.user.entities.UserProperties;
import com.jakubowski.spring.done.user.repositories.UserPropertiesRepository;
import com.jakubowski.spring.done.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPropertiesRepository userPropertiesRepository;

    public ResponseEntity<User> updateUser(long id, User user) {

        if(id == -1 || id == 0) {
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }

        userRepository.deleteById(id);
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<User> createUser(User user) {

//        if(userRepository.findById(user.getId()).isPresent()) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }

        if(userRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if(userRepository.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        UserProperties userProperties = new UserProperties("", "", true, "");
        user.setUserProperties(userProperties);
        userProperties.setUser(user);
        //userRepository.save(user);
        User createdUser = userRepository.save(user);
        //userPropertiesRepository.save(createdUser.getUserProperties());
        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdUser.getId())
                    .toUri();

        return ResponseEntity.created(uri).build();
    }

}
