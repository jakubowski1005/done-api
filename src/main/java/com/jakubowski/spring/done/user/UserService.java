package com.jakubowski.spring.done.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<User> updateUser(long id, User user) {

        if(id == -1 || id == 0) {
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }

        userRepository.deleteById(id);
        return null;
    }

    public ResponseEntity<User> createUser(User user) {

        if(userRepository.findById(user.getId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if(userRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if(userRepository.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
