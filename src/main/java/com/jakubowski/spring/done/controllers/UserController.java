package com.jakubowski.spring.done.controllers;

import com.jakubowski.spring.done.entities.TodoList;
import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.entities.UserProperties;
import com.jakubowski.spring.done.payloads.ApiResponse;
import com.jakubowski.spring.done.repositories.TodoListRepository;
import com.jakubowski.spring.done.repositories.UserPropertiesRepository;
import com.jakubowski.spring.done.repositories.UserRepository;
import com.jakubowski.spring.done.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    private TodoListRepository todoListRepository;

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

    @PutMapping("/users/{userId}/properties")
    @Transactional
    public ResponseEntity<?> updateUserProperties(@PathVariable long userId, @RequestBody UserProperties userProperties,
                                                  @RequestHeader(value = "Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!user.get().getUsername().equals(username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

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

    @PostMapping("/users/{userId}/lists")
    public ResponseEntity<?> createList(@PathVariable long userId, @RequestBody TodoList todoList,
                                        @RequestHeader(value = "Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!user.get().getUsername().equals(username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (user.get().getTodolists() != null) return new ResponseEntity<>(HttpStatus.CONFLICT);

        user.get().addTodoList(todoList);
        todoListRepository.save(todoList);

        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(todoList.getId())
                    .toUri();

        return ResponseEntity.created(uri).body(new ApiResponse(true, "Todo list added successfully!"));

    }

    @DeleteMapping("/users/{userId}/lists/{listId}")
    public ResponseEntity<?> deleteList(@PathVariable long userId, @PathVariable long listId,
                                        @RequestHeader(value = "Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!user.get().getUsername().equals(username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);


        todoListRepository.deleteById(listId);

        return ResponseEntity.noContent().build();
    }


}
