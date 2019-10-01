package com.jakubowski.spring.done.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakubowski.spring.done.entities.User;
import com.jakubowski.spring.done.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void helloTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/test")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World"));
    }

    @Test
    public void shouldReturnUserById() throws Exception {

        String URI = "/api/users/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        User found = new ObjectMapper().readValue(content, User.class);

        assertThat(mvcResult.getResponse().getStatus(), equalTo(200));
        assertThat(found, is(instanceOf(User.class)));
        //mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
