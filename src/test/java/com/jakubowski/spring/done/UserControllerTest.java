//package com.jakubowski.spring.done;
//
//import com.jakubowski.spring.done.controllers.UserController;
//import com.jakubowski.spring.done.entities.User;
//import com.jakubowski.spring.done.resources.Data;
//import com.jakubowski.spring.done.services.AuthService;
//import com.jakubowski.spring.done.services.UserService;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.awt.*;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserControllerTest {
//
//    private MockMvc mvc;
//
//    @Mock
//    UserService userService;
//
//    @Mock
//    AuthService authService;
//
//    @InjectMocks
//    UserController userController;
//
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
//    @Test
//    public void simpleTest() {
//
//        MockHttpServletResponse response = mvc.perform(get("/").)
//    }
//
////    @Test
////    public void userShouldBeRetrievedByID() throws Exception {
////
////        //given
////        User user = Data.getTestData();
////        given(userService.getUserById(anyLong(), anyString())).willReturn(user);
////        //User found = userController.getUserById(1L,"token");
////        User found = userService.getUserById(1L, "header");
////        System.out.println(found);
////
////        //when
////        //when(userService.getUserById(anyLong(), anyString())).thenReturn(user);
////        when(authService.isUserAuthorized(anyLong(), anyString())).thenReturn(true);
////
////        MockHttpServletResponse response = mvc
////                                            .perform(get("/api/users/1").accept(MediaType.APPLICATION_JSON))
////                                            .andReturn()
////                                            .getResponse();
////
////        //then
////        assertThat(response.getStatus(), equalTo(HttpStatus.OK));
////
////    }
//
//}
//
//
