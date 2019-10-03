package com.jakubowski.spring.done.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakubowski.spring.done.payloads.SignInRequest
import com.jakubowski.spring.done.payloads.SignUpRequest
import com.jakubowski.spring.done.services.AuthService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class AuthControllerSpec extends Specification{

    AuthController authController
    AuthService authService
    MockMvc mockMvc
    ObjectMapper objectMapper = new ObjectMapper()

    SignInRequest correctSignInRequest
    SignInRequest uncorrectSignInRequest
    SignUpRequest correctSignUpRequest
    SignUpRequest uncorrectSignUpRequest

    String correctSignInRequestJson
    String uncorrectSignInRequestJson
    String correctSignUpRequestJson
    String uncorrectSignUpRequestJson

    void setup() {
        authService = Mock(AuthService)
        authController = new AuthController([authService: authService])
        mockMvc = MockMvcBuilders.standaloneSetup(authController).alwaysDo(MockMvcResultHandlers.print()).build()

        correctSignInRequest = new SignInRequest([usernameOrEmail: 'correctCredential', password: 'pass'])
        uncorrectSignInRequest = new SignInRequest([usernameOrEmail: 'uncorrectCredential', password: 'pass'])
        correctSignUpRequest = new SignUpRequest([username: 'correctUsername', email: 'email@gmail.com', password: 'pass'])
        uncorrectSignUpRequest = new SignUpRequest([username: 'uncorrectUsername', email: 'email@gmail.com', password: 'pass'])

        correctSignInRequestJson = objectMapper.writeValueAsString(correctSignInRequest)
        uncorrectSignInRequestJson = objectMapper.writeValueAsString(uncorrectSignInRequest)
        correctSignUpRequestJson = objectMapper.writeValueAsString(correctSignUpRequest)
        uncorrectSignUpRequestJson = objectMapper.writeValueAsString(uncorrectSignUpRequest)
    }

    void ''
}
