package com.jakubowski.spring.done.services;


import com.jakubowski.spring.done.entities.Todo;
import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.repositories.TodoRepository;
import com.jakubowski.spring.done.repositories.UserRepository;
import com.jakubowski.spring.done.resources.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class StatsCalculatorTest {

    @Mock
    UserRepository userRepository;

    @Mock
    TodoRepository todoRepository;

    @Mock
    TodoListService todoListService;

    @InjectMocks
    StatsCalculator statsCalculator;

    @Test
    public void userStatisticsShouldRecalculateInProperWay() {

        //given
        User user = Data.getTestData();
        List<Todo> todos = Data.getAllTodosByUser(user);


        //when
        when(userRepository.existsById(any(Long.class))).thenReturn(true);
        when(userRepository.getOne(any(Long.class))).thenReturn(user);
        when(todoRepository.getAllByTodoList_User_Id(any(Long.class))).thenReturn(todos);
        when(todoListService.calculateCompleteLevel(1L)).thenReturn(0.66);
        when(todoListService.calculateCompleteLevel(2L)).thenReturn(0d);
        when(todoListService.calculateCompleteLevel(3L)).thenReturn(1d);

        statsCalculator.recalculateStats(user.getId());

        //then
        assertAll(
                () -> assertThat(user.getUserStatistics(), is(not(nullValue()))),
                () -> assertThat(user.getUserStatistics().getActiveLists(), is(equalTo(2))),
                () -> assertThat(user.getUserStatistics().getCompletedLists(), is(equalTo(1))),
                () -> assertThat(user.getUserStatistics().getCompletedTasks(), is(equalTo(4))),
                () -> assertThat(user.getUserStatistics().getDaysWithApp(), is(greaterThanOrEqualTo(0)))
        );
    }
}
