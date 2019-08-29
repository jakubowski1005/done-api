package com.jakubowski.spring.done.repositories;

import com.jakubowski.spring.done.entities.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
