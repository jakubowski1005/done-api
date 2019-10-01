package com.jakubowski.spring.done.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtAuthenticationResponse {

    private String accessToken;
    private final String tokenType = "Bearer";
}
