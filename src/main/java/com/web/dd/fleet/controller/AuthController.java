package com.web.dd.fleet.controller;

import com.web.dd.fleet.payload.ApiResponse;
import com.web.dd.fleet.payload.JwtAuthenticationResponse;
import com.web.dd.fleet.payload.LoginPayload;
import com.web.dd.fleet.payload.SignUpPayload;
import com.web.dd.fleet.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginPayload loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpPayload signUpPayload) {
        return authService.registerUser(signUpPayload);
    }
}