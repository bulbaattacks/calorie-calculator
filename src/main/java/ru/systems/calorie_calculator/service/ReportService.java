package ru.systems.calorie_calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.systems.calorie_calculator.dao.FoodIntakeDao;
import ru.systems.calorie_calculator.dto.IntakeType;
import ru.systems.calorie_calculator.dto.report.DailyReportDto;
import ru.systems.calorie_calculator.entity.FoodIntake;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final FoodIntakeDao dao;

    public DailyReportDto calculateDailyReport(Long id) {
        List<FoodIntake> entities = dao.findByUserIdAndDate(id, LocalDate.now());
        Map<IntakeType, Integer> caloriePerIntake = new EnumMap<>(IntakeType.class);
        entities.forEach(e -> {
            var intakeType = e.getId().getType();
            var calorie = e.getId().getMeal().getNutrition();
            caloriePerIntake.merge(intakeType, calorie, Integer::sum);
        });
        int totalCalorie = caloriePerIntake.values().stream().reduce(0, Integer::sum);
        return new DailyReportDto(caloriePerIntake, totalCalorie);
    }
}
