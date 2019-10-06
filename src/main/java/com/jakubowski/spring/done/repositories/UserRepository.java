package com.jakubowski.spring.done.repositories;

import com.jakubowski.spring.done.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    default Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> byUsername = findByUsername(usernameOrEmail);
        if (byUsername.isPresent()) return byUsername;

        Optional<User> byEmail = findByEmail(usernameOrEmail);
        if (byEmail.isPresent()) return byEmail;

        return Optional.empty();
    }

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
