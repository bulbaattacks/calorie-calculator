package ru.systems.calorie_calculator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.systems.calorie_calculator.dto.FoodIntakeDto;
import ru.systems.calorie_calculator.service.FoodIntakeService;

@RestController
@RequiredArgsConstructor
public class FoodIntakeController {

    private final FoodIntakeService service;

    @PostMapping("/food_intake")
    public void saveFoodIntake(@Valid @RequestBody FoodIntakeDto dto) {
        service.saveFoodIntake(dto);
    }
}