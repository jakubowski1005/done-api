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
    private TodoListRepository todoListRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private StatsCalculator statsCalculator;

    public List<Todo> getAllTodosFromList(long listId) {

        if (!todoListRepository.findById(listId).isPresent()) return null;
        return todoListRepository.getOne(listId).getTodos();
    }


    public Todo getTodoById(long listId, long todoId) {
        if (!todoListRepository.findById(listId).isPresent()) return null;
        if (!todoRepository.findById(todoId).isPresent()) return null;
        Todo todo = todoRepository.getOne(todoId);
        if (!todoListRepository.getOne(listId).getTodos().contains(todo)) return null;
        return todo;
    }


    public ResponseEntity<?> createTodo(long userId, long listId, Todo todo) {

        if(!todoListRepository.findById(listId).isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "List doesn't exist."), HttpStatus.NOT_FOUND);
        }

        TodoList list = todoListRepository.getOne(listId);
        list.getTodos().add(todo);
        todoRepository.save(todo);

        statsCalculator.recalculateStats(userId);
        logger.info("Todo created successfully.");
        return new ResponseEntity<>(new ApiResponse(true, "Todo added successfully!"), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateTodo(long userId, long listId, long todoId, Todo todo) {

        if(!todoListRepository.findById(listId).isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "List doesn't exist."), HttpStatus.NOT_FOUND);
        }

        TodoList todoList = todoListRepository.getOne(listId);

        if (todoList.getTodos() == null || !todoRepository.findById(todoId).isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Todo doesn't exist."), HttpStatus.NOT_FOUND);
        }

        Todo oldTodo = todoRepository.getOne(todoId);
        todoList.getTodos().remove(oldTodo);
        todoRepository.delete(oldTodo);
        todoList.getTodos().add(todo);
        todoRepository.save(todo);

        statsCalculator.recalculateStats(userId);
        logger.info("Todo id={} updated successfully.", todoId);
        return new ResponseEntity<>(new ApiResponse(true, "Todo updated successfully."), HttpStatus.OK);
    }


    public ResponseEntity<?> deleteTodo(long userId, long listId, long todoId) {

        if(!todoListRepository.findById(listId).isPresent() || !todoRepository.findById(todoId).isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "List or todo doesn't exist."), HttpStatus.NOT_FOUND);
        }

        Todo todo = todoRepository.getOne(todoId);
        todoRepository.delete(todo);
        todoListRepository.getOne(listId).getTodos().remove(todo);

        statsCalculator.recalculateStats(userId);
        logger.info("Todo id={} deleted successfully.", todoId);
        return new ResponseEntity<>(new ApiResponse(true, "Todo deleted successfully."), HttpStatus.NO_CONTENT);

    }
}
