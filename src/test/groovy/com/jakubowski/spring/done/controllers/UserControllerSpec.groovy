package com.jakubowski.spring.done.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakubowski.spring.done.entities.User
import com.jakubowski.spring.done.entities.UserProperties
import com.jakubowski.spring.done.entities.UserStatistics
import com.jakubowski.spring.done.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class UserControllerSpec extends Specification {

    UserController userController
    MockMvc mockMvc
    UserService userService
    ObjectMapper objectMapper = new ObjectMapper()

    User user1
    User user2
    String user1Json
    String user2Json
    UserProperties properties
    UserStatistics statistics
    String propertiesJson
    String statisticsJson

    void setup() {
        userService = Mock(UserService)
        userController = new UserController([userService: userService])
        mockMvc = MockMvcBuilders.standaloneSetup(userController).alwaysDo(MockMvcResultHandlers.print()).build()

        user1 = new User([username: 'user1', email: 'email@gmail.com', password: 'pass'])
        user2 = new User([username: 'user2', email: 'email1@gmail.com', password: 'pass'])

        properties = new UserProperties([id: 1L, name: 'name', lastName: 'surname', avatarURL: 'url'])
        statistics = new UserStatistics([id: 1L, completedTasks: 2, completedLists: 4, daysWithApp: 3, activeLists: 6])

        user1.setUserProperties(properties)
        user1.setUserStatistics(statistics)

        user1Json = objectMapper.writeValueAsString(user1)
        user2Json = objectMapper.writeValueAsString(user2)
        propertiesJson = objectMapper.writeValueAsString(properties)
        statisticsJson = objectMapper.writeValueAsString(statistics)
    }

    void 'get customer return customer by id'() {
        given:
        1 * userService.getUserById(1) >> user1

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get('/api/users/{id}', 1))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(user1Json))
    }

    void 'get properties return customers properties'() {
        given:
        1 * userService.getUserProperties(1) >> properties

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get('/api/users/{id}/properties', 1))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.content().json(propertiesJson))
    }

    void 'get statistics return customer statistics'() {
        given:
        1 * userService.getUserStatistics(1) >> statistics

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get('/api/users/{id}/statistics', 1))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.content().json(statisticsJson))
    }

    void 'should delete user'() {
        given:
        1 * userService.deleteUser(1) >> null

        expect:
        mockMvc.perform(MockMvcRequestBuilders.delete('/api/users/{id}', 1))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    }

    void 'should update existing user properties'() {
        given:
        1 * userService.updateUserProperties(1, properties) >>new ResponseEntity<>(properties, HttpStatus.OK)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.put('/api/users/{id}/properties', 1)
        .contentType(MediaType.APPLICATION_JSON).content(propertiesJson))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.content().json(propertiesJson))
    }

    void 'should update existing user statistics'() {
        given:
        1 * userService.updateUserStatistics(1, statistics) >> new ResponseEntity<>(statistics, HttpStatus.OK)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.put('/api/users/{id}/statistics', 1)
        .contentType(MediaType.APPLICATION_JSON).content(statisticsJson))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.content().json(statisticsJson))
    }
}
