package ru.starslan.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostNotFondException extends RuntimeException {
    public PostNotFondException(String message) {
        super(message);
    }
}
