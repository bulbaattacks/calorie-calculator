package ru.systems.calorie_calculator.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealDto {
        @NotBlank
        private String name;
        @Positive
        @Max(value = 1000)
        private Integer nutrition;
        @Positive
        @Max(value = 1000)
        private Integer protein;
        @Positive
        @Max(value = 1000)
        private Integer fat;
        @Positive
        @Max(value = 1000)
        private Integer carb;
}