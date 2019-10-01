//package com.jakubowski.spring.done.resources;
//
//import com.jakubowski.spring.done.entities.*;
//import com.jakubowski.spring.done.enums.Color;
//import com.jakubowski.spring.done.enums.Priority;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Data {
//
//    public static User getTestData() {
//
//        User user = new User("username", "email@gmail.com", "pass");
//        user.setId(1L);
//
//        TodoList firstList = new TodoList("firstList", Color.BLUE);
//        firstList.setId(1L);
//
//        Todo todo1 = new Todo("desc1", false, Priority.NORMAL);
//        firstList.addTodo(todo1);
//        Todo todo2 = new Todo("desc2", true, Priority.HIGH);
//        firstList.addTodo(todo2);
//        Todo todo3 = new Todo("desc3", true, Priority.URGENT);
//        firstList.addTodo(todo3);
//
//        TodoList secondList = new TodoList("secondList", Color.GREEN);
//        secondList.setId(2L);
//
//        Todo todo4 = new Todo("desc4", false, Priority.NORMAL);
//        secondList.addTodo(todo4);
//
//        TodoList thirdList = new TodoList("thirdList", Color.RED);
//        thirdList.setId(3L);
//
//        Todo todo5 = new Todo("desc5", true, Priority.HIGH);
//        thirdList.addTodo(todo5);
//        Todo todo6 = new Todo("desc6", true, Priority.URGENT);
//        thirdList.addTodo(todo6);
//
//        user.addTodoList(firstList);
//        user.addTodoList(secondList);
//        user.addTodoList(thirdList);
//
//        UserProperties userProperties = new UserProperties("name", "lastname", "avatar/url");
//        user.setUserProperties(userProperties);
//
//        return user;
//    }
//
//    public static List<Todo> getAllTodosByUser(User user) {
//        List<TodoList> lists = user.getTodolists();
//        List<Todo> todos = new ArrayList<>();
//        for(TodoList todoList:lists) {
//            for (int i = 0; i < todoList.getTodos().size(); i++) {
//                todos.add(todoList.getTodos().get(i));
//            }
//        }
//        return todos;
//    }
//}
