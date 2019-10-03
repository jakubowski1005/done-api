package com.jakubowski.spring.done.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakubowski.spring.done.entities.TodoList
import com.jakubowski.spring.done.enums.Color
import com.jakubowski.spring.done.services.TodoListService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class TodoListControllerSpec extends Specification {

    TodoListController controller
    TodoListService service
    MockMvc mockMvc
    ObjectMapper mapper = new ObjectMapper()

    List<TodoList> list = new ArrayList<>()
    TodoList todos1
    TodoList todos2

    String listJson
    String todos1Json
    String todos2Json

    void setup() {
        service = Mock(TodoListService)
        controller = new TodoListController([todoListService: service])
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(MockMvcResultHandlers.print()).build()

        todos1 = new TodoList([listname: 'todos1', color: Color.BLUE])
        todos2 = new TodoList([listname: 'todos2', color: Color.GREEN])

        list << todos1
        list << todos2

        listJson = mapper.writeValueAsString(list)
        todos1Json = mapper.writeValueAsString(todos1)
        todos2Json = mapper.writeValueAsString(todos2)
    }

    void 'get all return list of a todo lists'() {
        given:
        1 * service.getAllLists(1L) >> list

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get('/api/users/{userId}/lists', 1))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.content().json(listJson))
    }

    void 'get by id return correct a todo list'() {
        given:
        1 * service.getListById(1L) >> todos1

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get('/api/users/{userId}/lists/{listId}', 1, 1))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.content().json(todos1Json))
    }

    void 'should delete a list'() {
        given:
        1 * service.deleteList(1L, 1L) >> null

        expect:
        mockMvc.perform(MockMvcRequestBuilders.delete('/api/users/{userId}/lists/{listId}', 1, 1))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    }

    void 'should create a list'() {
        given:
        1 * service.createList(1L, todos2) >> new ResponseEntity<>(todos2, HttpStatus.CREATED)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post('/api/users/{userId}/lists', 1)
                .contentType(MediaType.APPLICATION_JSON).content(todos2Json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(todos2Json))
    }

    void 'should update a list'() {
        given:
        1 * service.updateList(1L, 1L, todos2) >> new ResponseEntity<>(todos2, HttpStatus.OK)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.put('/api/users/{userId}/lists/{listId}', 1, 1)
                .contentType(MediaType.APPLICATION_JSON).content(todos2Json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(todos2Json))
    }
}
