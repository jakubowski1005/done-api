package com.jakubowski.spring.done.services;

import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.entities.UserProperties;
import com.jakubowski.spring.done.entities.UserStatistics;
import com.jakubowski.spring.done.repositories.*;
import com.jakubowski.spring.done.resources.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserPropertiesRepository userPropertiesRepository;

    @Mock
    UserStatisticsRepository userStatisticsRepository;

    @Mock
    AuthService authService;

    @InjectMocks
    UserService userService;

    @Test
    public void shouldFindUserByItsID() {

        //given
        User user = Data.getTestData();

        //when
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        User found = userService.getUserById(user.getId());

        //then
        assertThat(found, equalTo(user));
    }

    @Test
    public void userShouldHasCorrectProperties() {

        //given
        User user = Data.getTestData();
        UserProperties userProperties = user.getUserProperties();

        //when
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        UserProperties foundProperties = userService.getUserProperties(user.getId());

        //then
        assertThat(foundProperties, equalTo(userProperties));
    }

    @Test
    public void shouldCreateNewUserPropertiesWhenNotFound() {

        //given
        User user = Data.getTestData();
        user.setUserProperties(null);

        //when
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        ResponseEntity res = userService.updateUserProperties(user.getId(), new UserProperties());

        //then
        assertAll(
                () -> assertThat(res.getStatusCode(), equalTo(HttpStatus.CREATED)),
                () -> assertThat(user.getUserProperties(), is(not(nullValue())))
        );
    }

    @Test
    public void shouldCreateNewUserStatisticsWhenNotFound() {

        //given
        User user = Data.getTestData();
        user.setUserStatistics(null);

        //when
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        ResponseEntity res = userService.updateUserStatistics(user.getId(), new UserStatistics());

        //then
        assertAll(
                () -> assertThat(res.getStatusCode(), equalTo(HttpStatus.CREATED)),
                () -> assertThat(user.getUserStatistics(), is(not(nullValue())))
        );
    }

    @Test
    public void shouldUpdateUserPropertiesWhenExists() {

        //given
        User user = Data.getTestData();
        UserProperties oldUserProperties = user.getUserProperties();

        //when
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        ResponseEntity res = userService.updateUserProperties(user.getId(), new UserProperties());

        //then
        assertAll(
                () -> assertThat(res.getStatusCode(), equalTo(HttpStatus.OK)),
                () -> assertThat(oldUserProperties, is(not(equalTo(user.getUserProperties()))))
        );
    }

    @Test
    public void shouldUpdateUserStatisticsWhenExists() {

        //given
        User user = Data.getTestData();
        user.setUserStatistics(new UserStatistics());
        UserStatistics oldUserStatistics = user.getUserStatistics();

        //when
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        ResponseEntity res = userService.updateUserStatistics(user.getId(), new UserStatistics());

        //then
        assertAll(
                () -> assertThat(res.getStatusCode(), equalTo(HttpStatus.OK)),
                () -> assertThat(oldUserStatistics, is(not(equalTo(user.getUserStatistics()))))
        );
    }
}
