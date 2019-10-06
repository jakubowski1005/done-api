package com.jakubowski.spring.done.security

import com.jakubowski.spring.done.entities.User
import com.jakubowski.spring.done.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification

class CustomUserDetailsServiceSpec extends Specification {

    UserRepository userRepository
    CustomUserDetailsService service
    User user

    void setup() {
        userRepository = Stub(UserRepository)
        service = new CustomUserDetailsService([userRepository: userRepository])
        user = new User('username', 'email@gmail.com', 'password')
    }

    void 'should return principal if correct credential'() {
        when:
        userRepository.findByUsernameOrEmail('username') >> Optional.of(user)
        userRepository.findByUsernameOrEmail('email@gmail.com') >> Optional.of(user)
        UserDetails userDetails1 = service.loadUserByUsername('username')
        UserDetails userDetails2 = service.loadUserByUsername('email@gmail.com')

        then:
        userDetails1.getUsername() == 'username'
        userDetails2.getUsername() == 'username'
        userDetails1.getPassword() == 'password'
        userDetails2.getPassword() == 'password'
    }

    void 'should throw an exception when invalid credentials'() {
        when:
        userRepository.findByUsernameOrEmail('email@gmail.com') >> Optional.empty()
        service.loadUserByUsername('email@gmail.com')

        then:
        thrown(UsernameNotFoundException)
    }
}
