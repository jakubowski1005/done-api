package com.jakubowski.spring.done.services

import com.jakubowski.spring.done.entities.Todo
import com.jakubowski.spring.done.entities.TodoList
import com.jakubowski.spring.done.entities.User
import com.jakubowski.spring.done.enums.Color
import com.jakubowski.spring.done.enums.Priority
import com.jakubowski.spring.done.payloads.ApiResponse
import com.jakubowski.spring.done.repositories.TodoListRepository
import com.jakubowski.spring.done.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class TodoListServiceSpec extends Specification {

    UserRepository userRepository
    TodoListRepository todoListRepository
    TodoListService todoListService
    StatsCalculator calculator

    User user
    List<TodoList> list = new ArrayList<>()
    TodoList todos1
    TodoList todos2

    void setup() {
        userRepository = Stub(UserRepository)
        todoListRepository = Stub(TodoListRepository)
        calculator = Stub(StatsCalculator)
        todoListService = new TodoListService([userRepository: userRepository, todoListRepository: todoListRepository, statsCalculator: calculator])

        user = new User(1L, "username", "email@gmail.com", "password", list, null, null)
        todos1 = new TodoList(1L, "name", Color.BLUE, null, user)
        todos2 = new TodoList(2L, "name", Color.BLUE, null, user)
        list << todos1 << todos2
    }

    void 'get all lists return all lists'() {
        when:
        userRepository.existsById(1L) >> true
        userRepository.getOne(1L) >> user
        List<TodoList> found = todoListService.getAllLists(1L)

        then:
        found == list
        found.size() == 2
        found.contains(todos1)
        found.contains(todos2)
    }

    void 'get all lists return null if user doesnt exist'() {
        when:
        userRepository.existsById(1L) >> false
        List found = todoListService.getAllLists(1L)

        then:
        found == null
    }

    void 'get list return list by id'() {
        when:
        todoListRepository.findById(1L) >> Optional.of(todos1)
        todoListRepository.findById(2L) >> Optional.of(todos2)
        TodoList found1 = todoListService.getListById(1L)
        TodoList found2 = todoListService.getListById(2L)

        then:
        found1 == todos1
        found2 == todos2
    }

    void 'get list return null if user doesnt exist'() {
        when:
        todoListRepository.findById(1L) >> Optional.empty()
        todoListRepository.findById(2L) >> Optional.empty()
        TodoList found1 = todoListService.getListById(1L)
        TodoList found2 = todoListService.getListById(2L)

        then:
        found1 == null
        found2 == null
    }

    void 'should delete list'() {
        when:
        userRepository.findById(1L) >> Optional.of(user)
        todoListRepository.getOne(1L) >> todos1
        todoListRepository.getOne(2L) >> todos2
        ResponseEntity response1 = todoListService.deleteList(1L, 1L)
        ResponseEntity response2 = todoListService.deleteList(1L, 2L)

        then:
        response1.getStatusCode() == HttpStatus.NO_CONTENT
        response2.getStatusCode() == HttpStatus.NO_CONTENT
    }

    void 'should not delete list if user doesnt exist'() {
        when:
        userRepository.findById(1L) >> Optional.empty()
        ResponseEntity response = todoListService.deleteList(1L, 1L)
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "User with ID 1 doesn't exist."
    }

    void 'should create list'() {
        when:
        userRepository.findById(1L) >> Optional.of(user)
        ResponseEntity response = todoListService.createList(1L, new TodoList())
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.CREATED
        apiResponse.getSuccess()
        apiResponse.getMessage() == "Todo list added successfully!"
    }

    void 'should not create list if user doesnt exist'() {
        when:
        userRepository.findById(1L) >> Optional.empty()
        ResponseEntity response = todoListService.createList(1L, new TodoList())
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "User with ID 1 doesn't exist."
    }

    void 'should update list'() {
        when:
        userRepository.findById(1L) >> Optional.of(user)
        todoListRepository.findById(1L) >> Optional.of(todos1)
        todoListRepository.getOne(1L) >> todos1
        ResponseEntity response = todoListService.updateList(1L, 1L, todos2)
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.OK
        apiResponse.getSuccess()
        apiResponse.getMessage() == "List updated successfully."
    }

    void 'should not update list if user doesnt exist'() {
        when:
        userRepository.findById(1L) >> Optional.empty()
        ResponseEntity response = todoListService.updateList(1L, 1L, todos2)
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "User with ID 1 doesn't exist."
    }

    void 'should not update list if list doesnt exist'() {
        when:
        userRepository.findById(1L) >> Optional.of(user)
        todoListRepository.findById(1L) >> Optional.empty()
        ResponseEntity response = todoListService.updateList(1L, 1L, todos2)
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "List or user doesn't exist."
    }

    void 'should correct calculate progress'() {
        List<Todo> todosList1 = new ArrayList<>()
        todosList1 << new Todo('1', false, Priority.URGENT)
        todosList1 << new Todo('2', true, Priority.NORMAL)

        List<Todo> todosList2 = new ArrayList<>()
        todosList2 << new Todo('1', false, Priority.URGENT)
        todosList2 << new Todo('2', true, Priority.URGENT)
        todosList2 << new Todo('3', true, Priority.URGENT)

        List<Todo> todosList3 = new ArrayList<>()
        todosList3 << new Todo('1', false, Priority.URGENT)
        todosList3 << new Todo('2', false, Priority.URGENT)
        todosList3 << new Todo('3', true, Priority.URGENT)

        todoListRepository.getOne(1L) >> listOfTodoLists

        expect:

        todoListService.calculateCompleteLevel(1L) == progress

        where:
        listOfTodoLists << [todosList1, todosList2, todosList3]
        progress << [0.5, 0.66, 0.33]

    }
}
