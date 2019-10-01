package com.jakubowski.spring.done.services;

import com.jakubowski.spring.done.entities.TodoList;
import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.repositories.TodoListRepository;
import com.jakubowski.spring.done.repositories.UserRepository;
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
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TodoListServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AuthService authService;

    @Mock
    TodoListRepository todoListRepository;

    @Mock
    StatsCalculator statsCalculator;

    @InjectMocks
    TodoListService todoListService;

    @Test
    public void shouldReturnAllUsersLists() {

        //given
        User user = Data.getTestData();

        //when
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(authService.isUserAuthorized(any(Long.class), any(String.class))).thenReturn(true);

        List<TodoList> lists = todoListService.getAllLists(user.getId(), "header");

        //then
        assertThat(lists, is(equalTo(user.getTodolists())));

    }

    @Test
    public void shouldReturnListById() {

        //given
        User user = Data.getTestData();
        TodoList list = user.getTodolists().get(0);

        //when
        when(authService.isUserAuthorized(any(Long.class), any(String.class))).thenReturn(true);
        when(todoListRepository.findById(any(Long.class))).thenReturn(Optional.of(user.getTodolists().get(0)));

        TodoList todoList = todoListService.getListById(user.getId(), list.getId(), "header");

        //then
        assertThat(todoList, is(equalTo(list)));
    }

    @Test
    public void shouldUpdateList() {

        //given
        User user = Data.getTestData();
        TodoList oldList = user.getTodolists().get(0);
        TodoList newList = new TodoList();

        //when
        when(authService.isUserAuthorized(any(Long.class), any(String.class))).thenReturn(true);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(todoListRepository.findById(any(Long.class))).thenReturn(Optional.of(user.getTodolists().get(0)));

        ResponseEntity res = todoListService.updateList(user.getId(), oldList.getId(), newList, "header");

        //then
        assertAll(
                () -> assertThat(res.getStatusCode(), equalTo(HttpStatus.OK)),
                () -> assertThat(user.getTodolists().get(2), equalTo(newList))
        );
    }

    @Test
    public void shouldCorrectlyCalculateProgress() {

        //given
        TodoList todoList = Data.getTestData().getTodolists().get(0);

        //when
        when(todoListRepository.getOne(anyLong())).thenReturn(todoList);
        double result = todoListService.calculateCompleteLevel(1L);

        //then
        assertThat(result, equalTo(2.0/3.0));
    }
}
