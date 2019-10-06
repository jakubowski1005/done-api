package com.jakubowski.spring.done.repositories

import com.jakubowski.spring.done.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
@DataJpaTest
@ContextConfiguration
class UserRepositorySpec extends Specification {

    @Autowired
    TestEntityManager entityManager

    @Autowired
    UserRepository userRepository

    User user


    void setup() {
        user = new User('johnny', 'email@gmail.com', 'password')
        entityManager.persist(user)
    }

    void 'user exists by username'() {
        expect:
        userRepository.existsByUsername('johnny')
        !userRepository.existsByUsername('bravo')
    }

    void 'user exists by e-mail'() {
        expect:
        userRepository.existsByEmail('email@gmail.com')
        !userRepository.existsByEmail('email@hotmail.com')
    }

    void 'should find by username or email'() {
        expect:
        userRepository.findByUsernameOrEmail('johnny', 'email@gmail.com') == Optional.of(user)
        userRepository.findByUsernameOrEmail('john', 'email@gmail.com') == Optional.of(user)
        userRepository.findByUsernameOrEmail('johnny', 'email123@gmail.com') == Optional.of(user)
        userRepository.findByUsernameOrEmail('johnny123', 'email123@gmail.com') == Optional.empty()
    }

    void cleanup() {
        entityManager.clear()
    }
}
