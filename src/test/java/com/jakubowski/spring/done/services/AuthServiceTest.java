//package com.jakubowski.spring.done.services;
//
//import com.jakubowski.spring.done.payloads.SignUpRequest;
//import com.jakubowski.spring.done.repositories.UserRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AuthServiceTest {
//
//    @Mock
//    PasswordEncoder passwordEncoder;
//
//    @Mock
//    UserRepository userRepository;
//
//    @InjectMocks
//    AuthService authService;
//
//    @Test
//    public void userCannotSignUpWithExistingUsername() {
//
//        //given
//        SignUpRequest signUpRequest = new SignUpRequest();
//
//        //when
//        when(userRepository.existsByUsername(anyString())).thenReturn(true);
//        ResponseEntity res = authService.registerUser(signUpRequest);
//
//        //then
//        assertThat(res.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
//    }
//
//    @Test
//    public void userCannotSignUpWithExistingEmail() {
//
//    }
//}
