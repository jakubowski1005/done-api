package com.jakubowski.spring.done.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPropertiesRepository extends JpaRepository<UserProperties, Long> {
}
