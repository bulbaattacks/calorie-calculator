package ru.systems.calorie_calculator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserDto {
    private Integer id;
    private Integer dailyCalorie;
}
