package com.jakubowski.spring.done.repositories;

import com.jakubowski.spring.done.entities.UserProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPropertiesRepository extends JpaRepository<UserProperties, Long> {
}
