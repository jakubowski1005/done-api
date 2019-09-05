package com.jakubowski.spring.done.controllers;

import com.jakubowski.spring.done.entities.Todo;
import com.jakubowski.spring.done.entities.TodoList;
import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.payloads.ApiResponse;
import com.jakubowski.spring.done.repositories.TodoListRepository;
import com.jakubowski.spring.done.repositories.UserRepository;
import com.jakubowski.spring.done.security.JwtProvider;
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
public class TodoListController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @GetMapping("/users/{userId}/lists")
    public List<TodoList> getAllLists(@PathVariable long userId,
                                      @RequestHeader(value = "Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) return null;

        if (!user.get().getUsername().equals(username)) return null;

        return user.get().getTodolists();
    }

    @GetMapping("/users/{userId}/lists/{listId}")
    public TodoList getListById(@PathVariable long userId, @PathVariable long listId,
                                @RequestHeader(value = "Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) return null;

        if (!user.get().getUsername().equals(username)) return null;

        return todoListRepository.getOne(userId);
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

    @PutMapping("/users/{userId}/lists/{listId}")
    @Transactional
    public ResponseEntity<?> updateList(@PathVariable long userId, @PathVariable long listId, @RequestBody TodoList todoList,
                                        @RequestHeader(value = "Authorization") String authorizationHeader) {

        if(!jwtProvider.isUserAuthorized(userId, authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId).get();

        if (user.getTodolists() != null || !todoListRepository.findById(listId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TodoList oldTodoList = todoListRepository.findById(listId).get();
        user.deleteTodoList(oldTodoList);
        todoListRepository.delete(oldTodoList);
        user.addTodoList(todoList);
        todoListRepository.save(todoList);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/users/{userId}/lists/{listId}")
    public ResponseEntity<?> deleteList(@PathVariable long userId, @PathVariable long listId,
                                        @RequestHeader(value = "Authorization") String authorizationHeader) {

        if(!jwtProvider.isUserAuthorized(userId, authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        TodoList todoList = todoListRepository.getOne(listId);
        todoListRepository.delete(todoList);
        userRepository.findById(userId).get().deleteTodoList(todoList);

        return ResponseEntity.noContent().build();
    }

}
