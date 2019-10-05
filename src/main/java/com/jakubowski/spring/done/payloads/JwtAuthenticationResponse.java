package com.jakubowski.spring.done.payloads;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtAuthenticationResponse {

    private String accessToken;
    private final String tokenType = "Bearer";
}
