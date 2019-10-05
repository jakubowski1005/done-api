package com.jakubowski.spring.done.services

import com.jakubowski.spring.done.entities.User
import com.jakubowski.spring.done.payloads.ApiResponse
import com.jakubowski.spring.done.payloads.JwtAuthenticationResponse
import com.jakubowski.spring.done.payloads.SignInRequest
import com.jakubowski.spring.done.payloads.SignUpRequest
import com.jakubowski.spring.done.repositories.UserRepository
import com.jakubowski.spring.done.security.JwtProvider
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class AuthServiceSpec extends Specification {

    AuthService service
    UserRepository repository
    AuthenticationManager auth
    JwtProvider jwt
    PasswordEncoder encoder

    SignInRequest signInRequest
    SignUpRequest signUpRequest

    void setup() {
        repository = Stub(UserRepository)
        auth = Mock(AuthenticationManager)
        encoder = Stub(PasswordEncoder)
        jwt = Stub(JwtProvider)
        service = new AuthService([userRepository: repository, authenticationManager: auth, passwordEncoder: encoder, jwtProvider: jwt])
    }

    void 'should login when using valid credentials'() {
        given:
        signInRequest = new SignInRequest([usernameOrEmail: 'correctCredential', password: 'password'])
        jwt.validateToken(_ as String) >> true

        when:
        ResponseEntity response = service.authenticateUser(signInRequest)

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody() instanceof JwtAuthenticationResponse
    }

    void 'should not login when using invalid credentials'() {
        given:
        signInRequest = new SignInRequest([usernameOrEmail: 'correctCredential', password: 'password'])
        jwt.validateToken(_ as String) >> false

        when:
        ResponseEntity response = service.authenticateUser(signInRequest)
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.UNAUTHORIZED
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "User doesn't exist"
    }

    void 'should register when using valid credentials'() {
        given:
        signUpRequest = new SignUpRequest([username: 'correctUsername', email: 'email@gmail.com', password: 'password'])
        repository.existsByUsername(_ as String) >> false
        repository.existsByEmail(_ as String) >> false
        repository.save(_ as User) >> null
        encoder.encode(_ as String) >> signUpRequest.getPassword()

        when:
        ResponseEntity response = service.registerUser(signUpRequest)
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.CREATED
        apiResponse.getSuccess()
        apiResponse.getMessage() == "User registered successfully!"
    }

    void 'should not register when using invalid username'() {
        given:
        signUpRequest = new SignUpRequest([username: 'correctUsername', email: 'email@gmail.com', password: 'password'])
        repository.existsByUsername(signUpRequest.getUsername()) >> true
        repository.existsByEmail(signUpRequest.getEmail()) >> false

        when:
        ResponseEntity response = service.registerUser(signUpRequest)
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.BAD_REQUEST
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "Username already taken!"
    }

    void 'should not register when using invalid email'() {
        given:
        signUpRequest = new SignUpRequest([username: 'correctUsername', email: 'email@gmail.com', password: 'password'])
        repository.existsByUsername(signUpRequest.getUsername()) >> false
        repository.existsByEmail(signUpRequest.getEmail()) >> true

        when:
        ResponseEntity response = service.registerUser(signUpRequest)
        ApiResponse apiResponse = (ApiResponse) response.getBody()

        then:
        response.getStatusCode() == HttpStatus.BAD_REQUEST
        !apiResponse.getSuccess()
        apiResponse.getMessage() == "E-mail already in use!"
    }
}
