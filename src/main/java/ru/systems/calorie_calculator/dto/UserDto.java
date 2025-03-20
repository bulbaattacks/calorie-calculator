package ru.systems.calorie_calculator.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
        @NotBlank
        private String name;
        @NotBlank
        @Email
        private String email;
        @Positive
        @Max(value = 130)
        private Integer age;
        @Positive
        @Max(value = 400)
        private Integer weight;
        @Positive
        @Max(value = 300)
        private Integer height;
        @NotNull
        private WeightAim weightAim;
}

enum WeightAim {LOSS, MAINTENANCE, GAIN}