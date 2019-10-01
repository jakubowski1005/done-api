package com.jakubowski.spring.done.controllers;

import com.jakubowski.spring.done.entities.TodoList;
import com.jakubowski.spring.done.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @GetMapping("/users/{userId}/lists")
    public List<TodoList> getAllLists(@PathVariable long userId) {
        return todoListService.getAllLists(userId);
    }

    @GetMapping("/users/{userId}/lists/{listId}")
    public TodoList getListById(@PathVariable long userId, @PathVariable long listId) {
        return todoListService.getListById(listId);
    }


    @PostMapping("/users/{userId}/lists")
    public ResponseEntity<?> createList(@PathVariable long userId, @RequestBody TodoList todoList) {
        return todoListService.createList(userId, todoList);
    }

    @PutMapping("/users/{userId}/lists/{listId}")
    @Transactional
    public ResponseEntity<?> updateList(@PathVariable long userId, @PathVariable long listId, @RequestBody TodoList todoList) {
        return todoListService.updateList(userId, listId, todoList);
    }


    @DeleteMapping("/users/{userId}/lists/{listId}")
    public ResponseEntity<?> deleteList(@PathVariable long userId, @PathVariable long listId) {
        return todoListService.deleteList(userId, listId);
    }

}
