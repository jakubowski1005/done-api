package com.jakubowski.spring.done.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 15)
    private final String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private final String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private final String password;
}