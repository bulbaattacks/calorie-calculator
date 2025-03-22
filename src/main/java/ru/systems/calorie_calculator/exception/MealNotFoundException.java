package ru.systems.calorie_calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MealNotFoundException extends ResponseStatusException {
    public static final String MEAL_NOT_FOUND = "Meal not found";

    public MealNotFoundException() {
        super(HttpStatus.BAD_REQUEST, MEAL_NOT_FOUND);
    }
}
