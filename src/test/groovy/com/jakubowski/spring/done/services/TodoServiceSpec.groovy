package com.jakubowski.spring.done.services

import com.jakubowski.spring.done.entities.Todo
import com.jakubowski.spring.done.entities.TodoList
import com.jakubowski.spring.done.enums.Color
import com.jakubowski.spring.done.enums.Priority
import com.jakubowski.spring.done.payloads.ApiResponse
import com.jakubowski.spring.done.repositories.TodoListRepository
import com.jakubowski.spring.done.repositories.TodoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class TodoServiceSpec extends Specification {

    TodoListRepository listRepository
    TodoRepository repository
    StatsCalculator calculator
    TodoService service

    TodoList todoList
    List<Todo> todos = new ArrayList<>()
    Todo todo1
    Todo todo2
    Todo todo3

    void setup() {
        listRepository = Stub(TodoListRepository)
        repository = Stub(TodoRepository)
        calculator = Mock(StatsCalculator)
        service = new TodoService([todoListRepository: listRepository, todoRepository: repository, statsCalculator: calculator])

        todoList = new TodoList(1L, 'todolist', Color.YELLOW, todos, null)
        todo1 = new Todo(1L, 'desc1', true, Priority.URGENT, todoList)
        todo2 = new Todo(2L, 'desc2', false, Priority.HIGH, todoList)
        todo3 = new Todo(3L, 'desc3', true, Priority.NORMAL, todoList)
        todos << todo1 << todo2 << todo3
    }

    void 'should return all todos'() {
        when:
        listRepository.findById(1L) >> Optional.of(todoList)
        listRepository.getOne(1L) >> todoList
        List<Todo> found = service.getAllTodosFromList(1L)

        then:
        found == todoList.getTodos()
        found.size() == 3
        found.get(1) == todo2
        found.contains(todo3)
    }

    void 'should not return todos if list doesnt exist'() {
        when:
        listRepository.findById(1L) >> Optional.empty()
        List<Todo> found = service.getAllTodosFromList(1L)

        then:
        found == null
    }

    void 'should return todo'() {
        when:
        listRepository.findById(1L) >> Optional.of(todoList)
        listRepository.getOne(1L) >> todoList
        repository.findById(2L) >> Optional.of(todo2)
        repository.getOne(2L) >> todo2
        Todo found = service.getTodoById(1L, 2L)

        then:
        found == todo2
    }

    void 'should not return todo when not existing'() {
        when:
        listRepository.getOne(1L) >> todoList
        repository.findById(2L) >> Optional.empty()
        Todo found = service.getTodoById(1L, 2L)

        then:
        found == null
    }

    void 'should not return todo when list doesnt exist'() {
        when:
        listRepository.findById(1L) >> Optional.empty()
        repository.findById(2L) >> Optional.of(todo2)
        repository.getOne(2L) >> todo2
        Todo found = service.getTodoById(1L, 2L)

        then:
        found == null
    }

    void 'should delete todo'() {
        when:
        listRepository.findById(1L) >> Optional.of(todoList)
        repository.findById(2L) >> Optional.of(todo2)
        repository.getOne(2L) >> todo2
        ResponseEntity response = service.deleteTodo(1L, 1L, 2L)
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.NO_CONTENT
        apiResponse.getSuccess()
        apiResponse.getMessage() == "Todo deleted successfully."
    }

    void 'should not delete todo if list or todo doesnt exist'() {
        when:
        listRepository.findById(1L) >> Optional.empty()
        repository.findById(2L) >> Optional.empty()
        repository.getOne(2L) >> todo2
        ResponseEntity response = service.deleteTodo(1L, 1L, 2L)
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "List or todo doesn't exist."
    }

    void 'should create todo'() {
        when:
        listRepository.findById(1L) >> Optional.of(todoList)
        listRepository.getOne(1L) >> todoList
        ResponseEntity response = service.createTodo(1L, 1L, new Todo())
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.CREATED
        apiResponse.getSuccess()
        apiResponse.getMessage() == "Todo added successfully!"
    }

    void 'should not create todo when list doesnt exist'() {
        when:
        listRepository.findById(1L) >> Optional.empty()
        listRepository.getOne(1L) >> todoList
        ResponseEntity response = service.createTodo(1L, 1L, new Todo())
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "List doesn't exist."
    }

    void 'should update todo'() {
        when:
        listRepository.findById(1L) >> Optional.of(todoList)
        listRepository.getOne(1L) >> todoList
        repository.findById(2L) >> Optional.of(todo2)
        repository.getOne(2L) >> todo2
        ResponseEntity response = service.updateTodo(1L, 1L,2L, new Todo())
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.OK
        apiResponse.getSuccess()
        apiResponse.getMessage() == "Todo updated successfully."
    }

    void 'should not update todo when todo doesnt exist'() {
        when:
        listRepository.findById(1L) >> Optional.of(todoList)
        listRepository.getOne(1L) >> todoList
        repository.findById(2L) >> Optional.empty()
        repository.getOne(2L) >> null
        ResponseEntity response = service.updateTodo(1L, 1L,2L, new Todo())
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "Todo doesn't exist."
    }

    void 'should not update todo when list doesnt exist'() {
        when:
        listRepository.findById(1L) >> Optional.empty()
        listRepository.getOne(1L) >> null
        repository.findById(2L) >> Optional.of(todo2)
        repository.getOne(2L) >> todo2
        ResponseEntity response = service.updateTodo(1L, 1L,2L, new Todo())
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "List doesn't exist."
    }
}
