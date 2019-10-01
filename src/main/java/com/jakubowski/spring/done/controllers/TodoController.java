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

    @GetMapping("/users/{userId}/lists/{listId}/todos")
    public List<Todo> getAllTodos(@PathVariable long userId, @PathVariable long listId) {
        return todoService.getAllTodosFromList(listId);
    }

    @GetMapping("/users/{userId}/lists/{listId}/todos/{todoId}")
    public Todo getTodoById(@PathVariable long userId, @PathVariable long listId, @PathVariable long todoId) {
        return todoService.getTodoById(listId, todoId);
    }


    @PostMapping("/users/{userId}/lists/{listId}/todos")
    public ResponseEntity<?> createList(@PathVariable long userId, @PathVariable long listId, @RequestBody Todo todo) {
        return todoService.createTodo(userId, listId, todo);
    }

    @PutMapping("/users/{userId}/lists/{listId}/todos/{todoId}")
    @Transactional
    public ResponseEntity<?> updateList(@PathVariable long userId, @PathVariable long listId, @RequestBody Todo todo,
                                        @PathVariable long todoId) {
        return todoService.updateTodo(userId, listId, todoId, todo);
    }

    @DeleteMapping("/users/{userId}/lists/{listId}/todos/{todoId}")
    public ResponseEntity<?> deleteList(@PathVariable long userId, @PathVariable long listId, @PathVariable long todoId) {
        return todoService.deleteTodo(userId, listId, todoId);
    }
}
