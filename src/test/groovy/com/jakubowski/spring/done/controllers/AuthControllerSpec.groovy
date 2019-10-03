package com.jakubowski.spring.done.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakubowski.spring.done.payloads.SignInRequest
import com.jakubowski.spring.done.payloads.SignUpRequest
import com.jakubowski.spring.done.services.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class AuthControllerSpec extends Specification{

    AuthController authController
    AuthService authService
    MockMvc mockMvc
    ObjectMapper objectMapper = new ObjectMapper()

    SignInRequest correctSignInRequest
    SignUpRequest correctSignUpRequest
    SignUpRequest uncorrectSignUpRequest

    String correctSignInRequestJson
    String correctSignUpRequestJson
    String uncorrectSignUpRequestJson

    void setup() {
        authService = Mock(AuthService)
        authController = new AuthController([authService: authService])
        mockMvc = MockMvcBuilders.standaloneSetup(authController).alwaysDo(MockMvcResultHandlers.print()).build()

        correctSignInRequest = new SignInRequest([usernameOrEmail: 'correctCredential', password: 'pass'])
        correctSignUpRequest = new SignUpRequest([username: 'correctUsername', email: 'email@gmail.com', password: 'password'])
        uncorrectSignUpRequest = new SignUpRequest([username: 'uncorrectUsername', email: 'email@gmail.com', password: '123'])

        correctSignInRequestJson = objectMapper.writeValueAsString(correctSignInRequest)
        correctSignUpRequestJson = objectMapper.writeValueAsString(correctSignUpRequest)
        uncorrectSignUpRequestJson = objectMapper.writeValueAsString(uncorrectSignUpRequest)
    }

    void 'should authenticate user'() {
        given:
        authService.authenticateUser(correctSignInRequest) >> new ResponseEntity<>(HttpStatus.OK)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post('/api/auth/signin')
                .contentType(MediaType.APPLICATION_JSON).content(correctSignInRequestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
    }

    void 'should successfully create new user'() {
        given:
        authService.registerUser(correctSignUpRequest) >> new ResponseEntity<>(HttpStatus.CREATED)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post('/api/auth/signup')
                .contentType(MediaType.APPLICATION_JSON).content(correctSignUpRequestJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    }

    void 'should return bad request when wrong credentials'() {
        given:
        authService.registerUser(uncorrectSignUpRequest) >> new ResponseEntity<>(HttpStatus.BAD_REQUEST)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post('/api/auth/signup')
                .contentType(MediaType.APPLICATION_JSON).content(uncorrectSignUpRequestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
    }
}
