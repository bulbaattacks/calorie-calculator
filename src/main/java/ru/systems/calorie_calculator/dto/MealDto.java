package ru.systems.calorie_calculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealDto {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id;
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