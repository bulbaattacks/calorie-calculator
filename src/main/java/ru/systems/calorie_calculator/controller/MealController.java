package ru.systems.calorie_calculator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.systems.calorie_calculator.dto.MealDto;
import ru.systems.calorie_calculator.service.MealService;

@RestController
@RequiredArgsConstructor
public class MealController {

    private final MealService service;

    @PostMapping("/meal")
    public MealDto saveMeal(@Valid @RequestBody MealDto dto) {
        return service.saveMeal(dto);
    }
}
