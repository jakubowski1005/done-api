package com.jakubowski.spring.done.repositories;

import com.jakubowski.spring.done.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByTodoList_User_Id(long userId);

    List<Todo> getAllByTodoList_User_Id(long userId);
}
