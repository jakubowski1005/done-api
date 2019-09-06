package com.jakubowski.spring.done.services;

import com.jakubowski.spring.done.entities.Todo;
import com.jakubowski.spring.done.entities.TodoList;
import com.jakubowski.spring.done.repositories.TodoListRepository;
import com.jakubowski.spring.done.repositories.TodoRepository;
import com.jakubowski.spring.done.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatsCalculator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TodoRepository todoRepository;

    public int calculateCompletedLists(long userId) {
        int counter = 0;
        List<TodoList> lists = userRepository.getOne(userId).getTodolists();

        for (TodoList todoList:lists) {
            if (todoList.getProgress() == 1) counter++;
        }
        return counter;
    }

    public int calculateCompletedTasks(long userId) {
        int counter = 0;
        List<Todo> todos =
    }
}
