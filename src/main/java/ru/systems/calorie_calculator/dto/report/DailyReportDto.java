package ru.systems.calorie_calculator.dto.report;

import ru.systems.calorie_calculator.dto.IntakeType;

import java.util.Map;

public record DailyReportDto(Map<IntakeType, Integer> caloriePerIntake, Integer totalCalorie) {
}
