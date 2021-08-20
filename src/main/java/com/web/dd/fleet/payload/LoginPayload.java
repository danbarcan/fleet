package com.web.dd.fleet.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPayload {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
