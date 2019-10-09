package com.jakubowski.spring.done.controllers;

import com.jakubowski.spring.done.entities.Todo;
import com.jakubowski.spring.done.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users/{userId}/lists/{listId}")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public List<Todo> getAllTodos(@PathVariable long listId) {
        return todoService.getAllTodosFromList(listId);
    }

    @GetMapping("/todos/{todoId}")
    public Todo getTodoById(@PathVariable long listId, @PathVariable long todoId) {
        return todoService.getTodoById(listId, todoId);
    }


    @PostMapping("/todos")
    public ResponseEntity<?> createList(@PathVariable long userId, @PathVariable long listId, @RequestBody Todo todo) {
        return todoService.createTodo(userId, listId, todo);
    }

    @PutMapping("/todos/{todoId}")
    @Transactional
    public ResponseEntity<?> updateList(@PathVariable long userId, @PathVariable long listId, @RequestBody Todo todo,
                                        @PathVariable long todoId) {
        return todoService.updateTodo(userId, listId, todoId, todo);
    }

    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<?> deleteList(@PathVariable long userId, @PathVariable long listId, @PathVariable long todoId) {
        return todoService.deleteTodo(userId, listId, todoId);
    }
}
