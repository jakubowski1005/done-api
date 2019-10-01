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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        user.get().getTodolists().add(todoList);
        todoListRepository.save(todoList);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(todoList.getId())
                .toUri();

        statsCalculator.recalculateStats(userId);
        return new ResponseEntity<>(new ApiResponse(true, "Todo list added successfully!"), HttpStatus.CREATED);

    }


    public ResponseEntity<?> updateList(long userId, long listId, TodoList todoList) {

        User user = userRepository.findById(userId).get();

        if (user.getTodolists() == null || !todoListRepository.findById(listId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TodoList oldTodoList = todoListRepository.findById(listId).get();
        user.getTodolists().remove(oldTodoList);
        todoListRepository.delete(oldTodoList);
        user.getTodolists().add(todoList);
        todoListRepository.save(todoList);

        statsCalculator.recalculateStats(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<?> deleteList(long userId, long listId) {

        TodoList todoList = todoListRepository.getOne(listId);
        todoListRepository.delete(todoList);
        userRepository.findById(userId).get().getTodolists().remove(todoList);

        statsCalculator.recalculateStats(userId);
        return ResponseEntity.noContent().build();
    }

    public double calculateCompleteLevel(long listId) {

        List<Todo> todos = todoListRepository.getOne(listId).getTodos();

        if(todos.size() == 0) return 0L;

        double completedTodos = 0;

        for (Todo todo : todos) {
            if(todo.isDone()) completedTodos++;
        }
        return (completedTodos/todos.size());
    }
}
