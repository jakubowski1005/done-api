package com.jakubowski.spring.done.resources;

import com.jakubowski.spring.done.entities.*;
import com.jakubowski.spring.done.enums.Color;
import com.jakubowski.spring.done.enums.Priority;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static User getTestData() {

        User user = new User("username", "email@gmail.com", "pass");
        user.setId(1L);

        TodoList firstList = new TodoList("firstList", Color.BLUE);
        firstList.setId(1L);

        Todo todo1 = new Todo("desc1", false, Priority.NORMAL);
        firstList.getTodos().add(todo1);
        Todo todo2 = new Todo("desc2", true, Priority.HIGH);
        firstList.getTodos().add(todo2);
        Todo todo3 = new Todo("desc3", true, Priority.URGENT);
        firstList.getTodos().add(todo3);

        TodoList secondList = new TodoList("secondList", Color.GREEN);
        secondList.setId(2L);

        Todo todo4 = new Todo("desc4", false, Priority.NORMAL);
        secondList.getTodos().add(todo4);

        TodoList thirdList = new TodoList("thirdList", Color.RED);
        thirdList.setId(3L);

        Todo todo5 = new Todo("desc5", true, Priority.HIGH);
        thirdList.getTodos().add(todo5);
        Todo todo6 = new Todo("desc6", true, Priority.URGENT);
        thirdList.getTodos().add(todo6);

        user.getTodolists().add(firstList);
        user.getTodolists().add(secondList);
        user.getTodolists().add(thirdList);

        UserProperties userProperties = new UserProperties(1L, "name", "lastname", "avatar/url");
        user.setUserProperties(userProperties);

        return user;
    }

    public static List<Todo> getAllTodosByUser(User user) {
        List<TodoList> lists = user.getTodolists();
        List<Todo> todos = new ArrayList<>();
        for(TodoList todoList:lists) {
            for (int i = 0; i < todoList.getTodos().size(); i++) {
                todos.add(todoList.getTodos().get(i));
            }
        }
        return todos;
    }
}
