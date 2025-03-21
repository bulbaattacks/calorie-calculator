package ru.systems.calorie_calculator.dto.report;

import ru.systems.calorie_calculator.dto.IntakeType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record HistoryReportDto(LocalDate foodIntakeDate, Map<IntakeType, IntakeTypeMealDto> caloriePerIntake) {

    public record IntakeTypeMealDto(Integer totalCalorie, List<FoodDto> foodList) {

        public record FoodDto(Integer quantity, String name) {
        }
    }
}
/*
 *дата 21 марта
 * BREAKFAST:  {
 *   totalCalorie:1000,
 *   foodList: [
 *       {qty:2, name:apple},
 *       {qty:3, name:potato},
 *   ]
 * }
 * обед: 1000
 * ужин: 2000
 * всего: 4000
 *
 * 22 марта
 *
 *
 * */