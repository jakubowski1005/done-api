package com.jakubowski.spring.done.controllers;

import com.jakubowski.spring.done.entities.Todo;
import com.jakubowski.spring.done.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/users/{userId}/lists{listId}/todos")
    public List<Todo> getAllTodos(@PathVariable long userId, @PathVariable long listId,
                                  @RequestHeader(value = "Authorization") String authorizationHeader) {
        return todoService.getAllTodosFromList(userId, listId, authorizationHeader);
    }

    @GetMapping("/users/{userId}/lists/{listId}/todos/{todoId}")
    public Todo getTodoById(@PathVariable long userId, @PathVariable long listId, @PathVariable long todoId,
                                @RequestHeader(value = "Authorization") String authorizationHeader) {
        return todoService.getTodoById(userId, listId, todoId, authorizationHeader);
    }


    @PostMapping("/users/{userId}/lists/{listId}/todos")
    public ResponseEntity<?> createList(@PathVariable long userId, @PathVariable long listId, @RequestBody Todo todo,
                                        @RequestHeader(value = "Authorization") String authorizationHeader) {
        return todoService.createTodo(userId, listId, todo, authorizationHeader);
    }

    @PutMapping("/users/{userId}/lists/{listId}/todos/{todoId}")
    @Transactional
    public ResponseEntity<?> updateList(@PathVariable long userId, @PathVariable long listId, @RequestBody Todo todo,
                                        @PathVariable long todoId, @RequestHeader(value = "Authorization") String authorizationHeader) {
        return todoService.updateTodo(userId, listId, todoId, todo, authorizationHeader);
    }

    @DeleteMapping("/users/{userId}/lists/{listId}/todos/{todoId}")
    public ResponseEntity<?> deleteList(@PathVariable long userId, @PathVariable long listId, @PathVariable long todoId,
                                        @RequestHeader(value = "Authorization") String authorizationHeader) {
        return todoService.deleteTodo(userId, listId, todoId, authorizationHeader);
    }
}
