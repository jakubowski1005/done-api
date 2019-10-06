package com.jakubowski.spring.done.services;

import com.jakubowski.spring.done.entities.Todo;
import com.jakubowski.spring.done.entities.TodoList;
import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.entities.UserStatistics;
import com.jakubowski.spring.done.repositories.TodoRepository;
import com.jakubowski.spring.done.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class StatsCalculator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoListService todoListService;

    void recalculateStats(long userId) {
        if (userRepository.existsById(userId)) {
            User user = userRepository.getOne(userId);
            if (user.getUserStatistics() == null) user.setUserStatistics(new UserStatistics());

            UserStatistics userStatistics = user.getUserStatistics();
            userStatistics.setCompletedTasks(calculateCompletedTasks(userId));
            userStatistics.setCompletedLists(calculateCompletedLists(userId));
            userStatistics.setActiveLists(calculateActiveLists(userId));
            userStatistics.setDaysWithApp(calculateDaysWithApp(userId));
        }
    }

    private int calculateCompletedLists(long userId) {

        int counter = 0;

        if (!userRepository.existsById(userId)) return counter;

        List<TodoList> lists = userRepository.getOne(userId).getTodolists();

        for (TodoList todoList:lists) {
            if (todoListService.calculateCompleteLevel(todoList.getId()) == 1) counter++;
        }
        return counter;
    }

    private int calculateCompletedTasks(long userId) {

        if (!userRepository.existsById(userId)) return 0;

        int counter = 0;
        List<Todo> todos = todoRepository.getAllByTodoList_User_Id(userId);

        for (Todo todo:todos) {
            if (todo.getIsDone()) {
                counter++;
            }
        }
        return counter;
    }

    private int calculateActiveLists(long userId) {
        return userRepository.getOne(userId).getTodolists().size() - calculateCompletedLists(userId);
    }

    private int calculateDaysWithApp(long userId) {
        LocalDate now = LocalDate.now();
        LocalDate creationTime = userRepository.getOne(userId).getCreationDate();
        long days = DAYS.between(creationTime, now);
        return (int) days;
    }
}
