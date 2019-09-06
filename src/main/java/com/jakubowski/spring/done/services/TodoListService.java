package com.jakubowski.spring.done.services;

import com.jakubowski.spring.done.entities.TodoList;
import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.payloads.ApiResponse;
import com.jakubowski.spring.done.repositories.TodoListRepository;
import com.jakubowski.spring.done.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {

    private final Logger logger = LoggerFactory.getLogger(TodoListService.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoListRepository todoListRepository;


    public List<TodoList> getAllLists(long userId, String authorizationHeader) {

        if(authService.isUserAuthorized(userId, authorizationHeader)) {
            return userRepository.findById(userId).get().getTodolists();
        }
        logger.warn("User with ID '{}' hasn't been authorized to get these lists.", userId);
        return null;
    }


    public TodoList getListById(long userId, long listId, String authorizationHeader) {

        if(!todoListRepository.findById(listId).isPresent()) {
            logger.warn("List with ID: '{}' doesn't exist.", listId);
            return null;
        }

        if(authService.isUserAuthorized(userId, authorizationHeader)) {
            return todoListRepository.findById(listId).get();
        }
        logger.warn("User with ID '{}' hasn't been authorized to get this list.", userId);
        return null;
    }



    public ResponseEntity<?> createList(long userId, TodoList todoList, String authorizationHeader) {

        if(!authService.isUserAuthorized(userId, authorizationHeader)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Optional<User> user = userRepository.findById(userId);
        user.get().addTodoList(todoList);
        todoListRepository.save(todoList);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(todoList.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new ApiResponse(true, "Todo list added successfully!"));

    }


    public ResponseEntity<?> updateList(long userId, long listId, TodoList todoList, String authorizationHeader) {

        if(!authService.isUserAuthorized(userId, authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId).get();

        if (user.getTodolists() == null || !todoListRepository.findById(listId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TodoList oldTodoList = todoListRepository.findById(listId).get();
        user.deleteTodoList(oldTodoList);
        todoListRepository.delete(oldTodoList);
        user.addTodoList(todoList);
        todoListRepository.save(todoList);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<?> deleteList(long userId, long listId, String authorizationHeader) {

        if(!authService.isUserAuthorized(userId, authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        TodoList todoList = todoListRepository.getOne(listId);
        todoListRepository.delete(todoList);
        userRepository.findById(userId).get().deleteTodoList(todoList);

        return ResponseEntity.noContent().build();
    }
}
