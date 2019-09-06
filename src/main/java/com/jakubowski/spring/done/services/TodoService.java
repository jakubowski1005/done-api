package com.jakubowski.spring.done.services;

import com.jakubowski.spring.done.entities.Todo;
import com.jakubowski.spring.done.entities.TodoList;
import com.jakubowski.spring.done.payloads.ApiResponse;
import com.jakubowski.spring.done.repositories.TodoListRepository;
import com.jakubowski.spring.done.repositories.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class TodoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthService authService;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TodoRepository todoRepository;


    public List<Todo> getAllTodosFromList(long userId, long listId, String authorizationHeader) {
        if (!authService.isUserAuthorized(userId, authorizationHeader)) return null;
        if (!todoListRepository.findById(listId).isPresent()) return null;
        return todoListRepository.getOne(listId).getTodos();
    }


    public Todo getTodoById(long userId, long listId, long todoId, String authorizationHeader) {
        if (!authService.isUserAuthorized(userId, authorizationHeader)) return null;
        if (!todoRepository.findById(todoId).isPresent()) return null;
        Todo todo = todoRepository.getOne(todoId);
        if (!todoListRepository.getOne(listId).getTodos().contains(todo)) return null;
        return todoRepository.getOne(todoId);
    }


    public ResponseEntity<?> createTodo(long userId, long listId, Todo todo, String authorizationHeader) {
        if(!authService.isUserAuthorized(userId, authorizationHeader)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        todoListRepository.getOne(listId).addTodo(todo);
        todoRepository.save(todo);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(todo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new ApiResponse(true, "Todo added successfully!"));
    }

    public ResponseEntity<?> updateTodo(long userId, long listId, long todoId, Todo todo, String authorizationHeader) {

        if(!authService.isUserAuthorized(userId, authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        TodoList todoList = todoListRepository.getOne(listId);

        if (todoList.getTodos() == null || !todoRepository.findById(todoId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Todo oldTodo = todoRepository.getOne(todoId);
        todoList.delete(oldTodo);
        todoRepository.delete(oldTodo);
        todoList.addTodo(todo);
        todoRepository.save(todo);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<?> deleteTodo(long userId, long listId, long todoId, String authorizationHeader) {

        if(!authService.isUserAuthorized(userId, authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Todo todo = todoRepository.getOne(todoId);
        todoRepository.delete(todo);
        todoListRepository.getOne(listId).delete(todo);

        return ResponseEntity.noContent().build();

    }
}
