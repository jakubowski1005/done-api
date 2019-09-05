package com.jakubowski.spring.done.repositories;

import com.jakubowski.spring.done.entities.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
}
