package com.jakubowski.spring.done.services;

import com.jakubowski.spring.done.entities.Todo;
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

import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {

    private final Logger logger = LoggerFactory.getLogger(TodoListService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private StatsCalculator statsCalculator;


    public List<TodoList> getAllLists(long userId) {
        if(!userRepository.existsById(userId)) return null;
        return userRepository.getOne(userId).getTodolists();
    }

    public TodoList getListById(long listId) {
        if(!todoListRepository.findById(listId).isPresent()) {
            logger.warn("List with ID: '{}' doesn't exist.", listId);
            return null;
        }
        return todoListRepository.findById(listId).get();
    }

    public ResponseEntity<?> createList(long userId, TodoList todoList) {

        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "User with ID " + userId + " doesn't exist."), HttpStatus.NOT_FOUND);
        }

        user.get().getTodolists().add(todoList);
        todoList.setUser(user.get());
        todoListRepository.save(todoList);
        statsCalculator.recalculateStats(userId);
        return new ResponseEntity<>(new ApiResponse(true, "Todo list added successfully!"), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateList(long userId, long listId, TodoList todoList) {

        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "User with ID " + userId + " doesn't exist."), HttpStatus.NOT_FOUND);
        }

        if (user.get().getTodolists() == null || !todoListRepository.findById(listId).isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "List or user doesn't exist."), HttpStatus.NOT_FOUND);
        }

        TodoList oldTodoList = todoListRepository.findById(listId).get();
        user.get().getTodolists().remove(oldTodoList);
        todoListRepository.delete(oldTodoList);
        todoList.setUser(user.get());
        user.get().getTodolists().add(todoList);
        todoListRepository.save(todoList);

        statsCalculator.recalculateStats(userId);
        return new ResponseEntity<>(new ApiResponse(true, "List updated successfully."), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteList(long userId, long listId) {
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "User with ID " + userId + " doesn't exist."), HttpStatus.NOT_FOUND);
        }

        TodoList todoList = todoListRepository.getOne(listId);
        todoListRepository.delete(todoList);
        user.get().getTodolists().remove(todoList);
        statsCalculator.recalculateStats(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public double calculateCompleteLevel(long listId) {

        List<Todo> todos = todoListRepository.getOne(listId).getTodos();

        if(todos.size() == 0) return 0L;

        double completedTodos = 0;

        for (Todo todo : todos) {
            if(todo.getIsDone()) completedTodos++;
        }
        return (completedTodos/todos.size());
    }
}
