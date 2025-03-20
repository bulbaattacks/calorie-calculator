package ru.systems.calorie_calculator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.systems.calorie_calculator.dto.UserDto;
import ru.systems.calorie_calculator.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/user")
    public void addUser(@Valid @RequestBody UserDto userDto) {
        service.saveUser(userDto);
    }
}
