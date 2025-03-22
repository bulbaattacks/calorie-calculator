package ru.systems.calorie_calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public static final String USER_NOT_FOUND = "User not found";

    public UserNotFoundException() {
        super(HttpStatus.BAD_REQUEST, USER_NOT_FOUND);
    }
}
