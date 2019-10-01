package com.jakubowski.spring.done.services;

import com.jakubowski.spring.done.entities.Todo;
import com.jakubowski.spring.done.entities.TodoList;
import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.repositories.TodoListRepository;
import com.jakubowski.spring.done.repositories.TodoRepository;
import com.jakubowski.spring.done.resources.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

    @Mock
    TodoListRepository todoListRepository;

    @Mock
    TodoRepository todoRepository;

    @Mock
    AuthService authService;

    @Mock
    StatsCalculator statsCalculator;

    @InjectMocks
    TodoService todoService;


    @Test
    public void allTodosShouldBeReturned() {

        //given
        User user = Data.getTestData();
        TodoList list = user.getTodolists().get(0);

        //when
        when(authService.isUserAuthorized(anyLong(), anyString())).thenReturn(true);
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.of(list));
        when(todoListRepository.getOne(anyLong())).thenReturn(list);

        List<Todo> todos = todoService.getAllTodosFromList(user.getId(), list.getId(), "header");

        //then
        assertThat(todos, equalTo(list.getTodos()));
    }

    @Test
    public void shouldReturnsTodoByItsID() {

        //given
        User user = Data.getTestData();
        TodoList list = user.getTodolists().get(0);
        Todo todo = list.getTodos().get(0);
        todo.setId(1L);

        //when
        when(authService.isUserAuthorized(anyLong(), anyString())).thenReturn(true);
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.of(list));
        when(todoListRepository.getOne(anyLong())).thenReturn(list);
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(todo));
        when(todoRepository.getOne(anyLong())).thenReturn(todo);

        Todo found = todoService.getTodoById(user.getId(), list.getId(), todo.getId(), "header");

        //then
        assertThat(found, equalTo(todo));
    }

    @Test
    public void shouldUpdateTodo() {
        //given
        User user = Data.getTestData();
        TodoList list = user.getTodolists().get(0);
        Todo oldTodo = list.getTodos().get(0);
        oldTodo.setId(1L);
        Todo newTodo = new Todo();

        //when
        when(authService.isUserAuthorized(any(Long.class), any(String.class))).thenReturn(true);
        when(todoListRepository.getOne(anyLong())).thenReturn(list);
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.of(list));
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(oldTodo));
        when(todoRepository.getOne(anyLong())).thenReturn(oldTodo);

        ResponseEntity res = todoService.updateTodo(user.getId(), list.getId(), oldTodo.getId(), newTodo, "header");

        //then
        assertAll(
                () -> assertThat(res.getStatusCode(), equalTo(HttpStatus.OK)),
                () -> assertThat(list.getTodos().get(list.getTodos().size()-1), equalTo(newTodo))
        );
    }
}
