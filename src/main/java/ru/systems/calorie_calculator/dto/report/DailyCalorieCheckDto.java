package ru.systems.calorie_calculator.dto.report;

public record DailyCalorieCheckDto(Integer actualCalorie,
                                   Integer targetCalorie,
                                   Integer differenceCalorie,
                                   Boolean isDailyCalorieNormExceeded) {
}
