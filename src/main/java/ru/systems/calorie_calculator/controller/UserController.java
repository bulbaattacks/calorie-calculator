package ru.systems.calorie_calculator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.systems.calorie_calculator.dto.RequestUserDto;
import ru.systems.calorie_calculator.dto.ResponseUserDto;
import ru.systems.calorie_calculator.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/user")
    public ResponseUserDto addUser(@Valid @RequestBody RequestUserDto dto) {
        return service.saveUser(dto);
    }
}
