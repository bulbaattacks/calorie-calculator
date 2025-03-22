package ru.systems.calorie_calculator.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class FoodIntakeDto {
    @NotNull
    private IntakeType intakeType;
    @NotNull
    private Long userId;
    @NotNull
    private Set<Long> mealIds;
}

