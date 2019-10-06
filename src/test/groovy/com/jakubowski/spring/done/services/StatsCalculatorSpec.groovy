package com.jakubowski.spring.done.services

import com.jakubowski.spring.done.entities.Todo
import com.jakubowski.spring.done.entities.TodoList
import com.jakubowski.spring.done.entities.User
import com.jakubowski.spring.done.entities.UserStatistics
import com.jakubowski.spring.done.enums.Color
import com.jakubowski.spring.done.enums.Priority
import com.jakubowski.spring.done.repositories.TodoRepository
import com.jakubowski.spring.done.repositories.UserRepository
import spock.lang.Specification

class StatsCalculatorSpec extends Specification {

    UserRepository userRepository
    TodoRepository todoRepository
    TodoListService todoListService
    StatsCalculator statsCalculator

    User user
    List<TodoList> lists = new ArrayList<>()
    TodoList todoList1
    TodoList todoList2
    List<Todo> todos1 = new ArrayList<>()
    List<Todo> todos2 = new ArrayList<>()
    Todo todo1
    Todo todo2
    Todo todo3
    Todo todo4
    Todo todo5
    List<Todo> todos3 = new ArrayList<>()

    void setup() {
        userRepository = Stub(UserRepository)
        todoRepository = Stub(TodoRepository)
        todoListService = Stub(TodoListService)
        statsCalculator = new StatsCalculator(
                [userRepository: userRepository, todoRepository: todoRepository, todoListService: todoListService]
        )

        user = new User(1L, 'username', 'email@gmail.com', 'password', lists, null, new UserStatistics())
        todoList1 = new TodoList(1L, 'listname', Color.BLUE, todos1, user)
        todoList2 = new TodoList(2L, 'listname', Color.ORANGE, todos2, user)
        lists << todoList1 << todoList2
        todo1 = new Todo(1L, 'desc1', true, Priority.URGENT, todoList1)
        todo2 = new Todo(2L, 'desc2', false, Priority.HIGH, todoList1)
        todo3 = new Todo(3L, 'desc3', true, Priority.NORMAL, todoList1)
        todo4 = new Todo(4L, 'desc4', true, Priority.HIGH, todoList2)
        todo5 = new Todo(5L, 'desc5', true, Priority.NORMAL, todoList2)
        todos1 << todo1 << todo2 << todo3
        todos2 << todo4 << todo5
        todos3 << todo1 << todo2 << todo3 << todo4 << todo5
    }

    void 'return proper number of days'() {
        when:
        userRepository.existsById(1L) >> true
        userRepository.getOne(1L) >> user
        statsCalculator.recalculateStats(1L)
        int days = user.getUserStatistics().getDaysWithApp()

        then:
        days == 0
    }

    void 'return 0 days if user doesnt exist'() {
        when:
        userRepository.existsById(1L) >> false
        statsCalculator.recalculateStats(1L)
        int days = user.getUserStatistics().getDaysWithApp()

        then:
        days == 0
    }

    void 'calculate proper number of active lists'() {
        when:
        userRepository.existsById(1L) >> true
        userRepository.getOne(1L) >> user
        todoListService.calculateCompleteLevel(1L) >> 1/2d
        todoListService.calculateCompleteLevel(2L) >> 1d
        statsCalculator.recalculateStats(1L)
        int active = user.getUserStatistics().getActiveLists()

        then:
        active == 1
    }

    void 'return 0 lists if user doesnt exist'() {
        when:
        userRepository.existsById(1L) >> false
        statsCalculator.recalculateStats(1L)
        int active = user.getUserStatistics().getActiveLists()

        then:
        active == 0
    }

    void 'calculate completed tasks'() {
        when:
        userRepository.existsById(1L) >> true
        userRepository.getOne(1L) >> user
        todoRepository.getAllByTodoList_User_Id(1L) >> todos3
        statsCalculator.recalculateStats(1L)
        int tasks = user.getUserStatistics().getCompletedTasks()

        then:
        tasks == 4
    }

    void 'return 0 tasks if user doesnt exist'() {
        when:
        userRepository.existsById(1L) >> false
        statsCalculator.recalculateStats(1L)
        int tasks = user.getUserStatistics().getCompletedTasks()

        then:
        tasks == 0
    }

    void 'calculate completed lists'() {
        when:
        userRepository.existsById(1L) >> true
        userRepository.getOne(1L) >> user
        todoListService.calculateCompleteLevel(1L) >> 1/2d
        todoListService.calculateCompleteLevel(2L) >> 1d
        statsCalculator.recalculateStats(1L)
        int completed = user.getUserStatistics().getCompletedLists()

        then:
        completed == 1
    }

    void 'return 0 completed lists if user doesnt exist'() {
        when:
        userRepository.existsById(1L) >> false
        statsCalculator.recalculateStats(1L)
        int lists = user.getUserStatistics().getCompletedLists()

        then:
        lists == 0
    }

}
