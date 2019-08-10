package com.jakubowski.spring.done.user.repositories;

import com.jakubowski.spring.done.user.entities.UserProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPropertiesRepository extends JpaRepository<UserProperties, Long> {
}
