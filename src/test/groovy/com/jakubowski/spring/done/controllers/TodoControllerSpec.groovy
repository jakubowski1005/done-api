package com.jakubowski.spring.done.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakubowski.spring.done.entities.Todo
import com.jakubowski.spring.done.enums.Priority
import com.jakubowski.spring.done.services.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class TodoControllerSpec extends Specification {

    TodoController controller
    TodoService service
    MockMvc mockMvc
    ObjectMapper mapper = new ObjectMapper()

    List<Todo> todos
    Todo todo1
    Todo todo2

    String todosJson
    String todo1Json
    String todo2Json

    void setup() {
        service = Mock(TodoService)
        controller = new TodoController([todoService: service])
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(MockMvcResultHandlers.print()).build()

        todos = new ArrayList<>()
        todo1 = new Todo('desc', false, Priority.NORMAL)
        todo2 = new Todo('desc', true, Priority.URGENT)

        todos << todo1
        todos << todo2

        todosJson = mapper.writeValueAsString(todos)
        todo1Json = mapper.writeValueAsString(todo1)
        todo2Json = mapper.writeValueAsString(todo2)
    }

    void 'get all return list of todos'() {
        given:
        1 * service.getAllTodosFromList(1L) >> todos

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get('/api/users/{userId}/lists/{listId}/todos', 1, 1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(todosJson))
    }

    void 'get by id return todo'() {
        given:
        1 * service.getTodoById(1L, 1L) >> todo1

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get('/api/users/{userId}/lists/{listId}/todos/{todoId}', 1, 1, 1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(todo1Json))
    }

    void 'create todo correctly'() {
        given:
        1 * service.createTodo(1L, 1L, todo2) >> new ResponseEntity<>(todo2, HttpStatus.CREATED)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post('/api/users/{userId}/lists/{listId}/todos', 1, 1)
                .contentType(MediaType.APPLICATION_JSON).content(todo2Json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(todo2Json))
    }

    void 'update todo correctly'() {
        given:
        1 * service.updateTodo(1L, 1L, 1L, todo2) >> new ResponseEntity<>(todo2, HttpStatus.OK)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.put('/api/users/{userId}/lists/{listId}/todos/{todoId}', 1, 1, 1)
                .contentType(MediaType.APPLICATION_JSON).content(todo2Json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(todo2Json))
    }

    void 'delete todo correctly'() {
        given:
        1 * service.deleteTodo(1L, 1L, 1L) >> null

        expect:
        mockMvc.perform(MockMvcRequestBuilders.delete('/api/users/{userId}/lists/{listId}/todos/{todoId}', 1, 1, 1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    }
}
