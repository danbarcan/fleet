package com.web.dd.fleet.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private UserPayload user;
}
