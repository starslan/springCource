package ru.starslan.demo.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "username not empty")
    private String username;

    @NotEmpty(message = "password not empty")
    private String password;
}
