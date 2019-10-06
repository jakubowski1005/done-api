package com.jakubowski.spring.done.repositories

import com.jakubowski.spring.done.entities.Todo
import com.jakubowski.spring.done.entities.TodoList
import com.jakubowski.spring.done.entities.User
import com.jakubowski.spring.done.enums.Color
import com.jakubowski.spring.done.enums.Priority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
@ContextConfiguration
@DataJpaTest
class TodoRepositorySpec extends Specification {

    @Autowired
    TestEntityManager entityManager

    @Autowired
    TodoRepository todoRepository

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
        user = new User('username', 'email@gmail.com', 'password')
        todoList1 = new TodoList('listname1', Color.BLUE)
        todoList2 = new TodoList('listname2', Color.ORANGE)
        lists << todoList1 << todoList2
        todo1 = new Todo( 'desc1', true, Priority.URGENT)
        todo2 = new Todo( 'desc2', false, Priority.HIGH)
        todo3 = new Todo( 'desc3', true, Priority.NORMAL)
        todo4 = new Todo( 'desc4', true, Priority.HIGH)
        todo5 = new Todo( 'desc5', true, Priority.NORMAL)
        todos1 << todo1 << todo2 << todo3
        todos2 << todo4 << todo5
        todos3 << todo1 << todo2 << todo3 << todo4 << todo5

        user.setTodolists(lists)
        todoList1.setTodos(todos1)
        todoList1.setUser(user)
        todoList2.setTodos(todos2)
        todoList2.setUser(user)
        todo1.setTodoList(todoList1)
        todo2.setTodoList(todoList1)
        todo3.setTodoList(todoList1)
        todo4.setTodoList(todoList2)
        todo5.setTodoList(todoList2)

        entityManager.persist(user)
        entityManager.persist(todoList1)
        entityManager.persist(todoList2)
        entityManager.persist(todo1)
        entityManager.persist(todo2)
        entityManager.persist(todo3)
        entityManager.persist(todo4)
        entityManager.persist(todo5)

    }

    void 'should return all users todos'() {
        expect:
        todoRepository.getAllByTodoList_User_Id(1L).size() == 5
        todoRepository.getAllByTodoList_User_Id(1L).get(4) == todo5
        todoRepository.getAllByTodoList_User_Id(1L).contains(todo3)
    }

    void cleanup() {
        entityManager.clear()
    }

}
