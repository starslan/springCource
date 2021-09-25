package ru.starslan.demo.payload.request;

import lombok.Data;
import ru.starslan.demo.annotations.PasswordMatches;
import ru.starslan.demo.annotations.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignRequest {

    @Email(message = "формат email" )
    @NotBlank(message = "email не пустой" )
    @ValidEmail
    private String email;
    @NotBlank(message = "firstname не пустой" )
    private String firstname;
    @NotBlank(message = "lastname не пустой" )
    private String lastname;
    @NotBlank(message = "username не пустой" )
    private String username;
    @NotBlank(message = "password не пустой" )
    @Size(min = 6)
    private String password;
    private String confirmPassword;
}
