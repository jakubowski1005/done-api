package com.jakubowski.spring.done.repositories;

import com.jakubowski.spring.done.entities.UserProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPropertiesRepository extends JpaRepository<UserProperties, Long> {
}
