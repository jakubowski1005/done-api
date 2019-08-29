package com.jakubowski.spring.done.repositories;

import com.jakubowski.spring.done.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
