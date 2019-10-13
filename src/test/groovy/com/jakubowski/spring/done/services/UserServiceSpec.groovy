package com.jakubowski.spring.done.services

import com.jakubowski.spring.done.entities.User
import com.jakubowski.spring.done.entities.UserProperties
import com.jakubowski.spring.done.entities.UserStatistics
import com.jakubowski.spring.done.repositories.UserPropertiesRepository
import com.jakubowski.spring.done.repositories.UserRepository
import com.jakubowski.spring.done.repositories.UserStatisticsRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class UserServiceSpec extends Specification {

    UserRepository repository
    UserPropertiesRepository propertiesRepository
    UserStatisticsRepository statisticsRepository
    UserService service

    User user
    UserProperties properties
    UserStatistics statistics

    void setup() {
        repository = Stub(UserRepository)
        propertiesRepository = Stub(UserPropertiesRepository)
        statisticsRepository = Stub(UserStatisticsRepository)
        service = new UserService(
                [userRepository: repository, userPropertiesRepository: propertiesRepository, userStatisticsRepository: statisticsRepository]
        )

        properties = new UserProperties(1L, "name", "surname", "url")
        statistics = new UserStatistics(1L, 1, 2, 3, 4)
        user = new User(1L, "username", "email@gmail.com", "password", null, properties, statistics)
    }

    void 'get user should return user by id'() {
        when:
        repository.findById(1L) >> Optional.of(user)
        User found = service.getUserById(1L)

        then:
        found == user
    }

    void 'get user by username or email should return correct user'() {
        when:
        repository.findByUsernameOrEmail('sth') >> user
        User found = service.getUserByUsernameOrEmail('sth')

        then:
        found == user
    }

    void 'get user by username or email should return null when user doesnt exist'() {
        when:
        repository.findByUsernameOrEmail('sth') >> null
        User found = service.getUserByUsernameOrEmail('sth')

        then:
        found == null
    }

    void 'get user props should return props by id'() {
        when:
        repository.findById(1L) >> Optional.of(user)
        UserProperties found = service.getUserProperties(1L)

        then:
        found == properties
    }

    void 'get user stats should return stats by id'() {
        when:
        repository.findById(1L) >> Optional.of(user)
        UserStatistics found = service.getUserStatistics(1L)

        then:
        found == statistics
    }

    void 'should delete user'() {
        when:
        repository.deleteById(1L) >> null
        ResponseEntity response = service.deleteUser(1L)

        then:
        response.getStatusCode() == HttpStatus.NO_CONTENT
    }

    void 'should update existing props'() {
        given:
        UserProperties newProps = new UserProperties(2L, "name", "surname", "avatarUrl")

        when:
        repository.findById(1L) >> Optional.of(user)
        ResponseEntity response = service.updateUserProperties(1L, newProps)

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody() == newProps
    }

    void 'should update existing stats'() {
        given:
        UserStatistics newStats = new UserStatistics(2L, 9, 7, 8, 7)

        when:
        repository.findById(1L) >> Optional.of(user)
        ResponseEntity response = service.updateUserStatistics(1L, newStats)

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody() == newStats
    }

    void 'should create unexisting props'() {
        given:
        UserProperties newProps = new UserProperties(2L, "name", "surname", "avatarUrl")
        user.setUserProperties(null)

        when:
        repository.findById(1L) >> Optional.of(user)
        ResponseEntity response = service.updateUserProperties(1L, newProps)

        then:
        response.getStatusCode() == HttpStatus.CREATED
        response.getBody() == newProps
    }

    void 'should create unexisting stats'() {
        given:
        UserStatistics newStats = new UserStatistics(2L, 9, 7, 8, 7)
        user.setUserStatistics(null)

        when:
        repository.findById(1L) >> Optional.of(user)
        ResponseEntity response = service.updateUserStatistics(1L, newStats)

        then:
        response.getStatusCode() == HttpStatus.CREATED
        response.getBody() == newStats
    }
}
