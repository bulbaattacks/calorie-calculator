package ru.systems.calorie_calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.systems.calorie_calculator.dao.FoodIntakeDao;
import ru.systems.calorie_calculator.dao.UserDao;
import ru.systems.calorie_calculator.dto.IntakeType;
import ru.systems.calorie_calculator.dto.report.DailyCalorieCheckDto;
import ru.systems.calorie_calculator.dto.report.DailyReportDto;
import ru.systems.calorie_calculator.dto.report.HistoryReportDto;
import ru.systems.calorie_calculator.entity.FoodIntake;
import ru.systems.calorie_calculator.entity.Meal;
import ru.systems.calorie_calculator.entity.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final FoodIntakeDao dao;
    private final UserDao userDao;

    public DailyReportDto calculateDailyReport(Long userId) {
        List<FoodIntake> userFoodIntakes = dao.findAllByDateAndUserId(LocalDate.now(), userId);
        Map<IntakeType, Integer> caloriePerIntake = new EnumMap<>(IntakeType.class);
        userFoodIntakes.forEach(foodIntake -> {
            var intakeType = foodIntake.getType();
            var calorie = foodIntake.getMeal().getNutrition();
            caloriePerIntake.merge(intakeType, calorie, Integer::sum);
        });
        int totalCalorie = caloriePerIntake.values().stream().reduce(0, Integer::sum);
        return new DailyReportDto(caloriePerIntake, totalCalorie);
    }

    public DailyCalorieCheckDto calculateCalorieReport(Long userId) {
        List<FoodIntake> userFoodIntakes = dao.findAllByDateAndUserId(LocalDate.now(), userId);
        int actualCalorie = userFoodIntakes
                .stream()
                .map(foodIntake -> foodIntake.getMeal().getNutrition())
                .mapToInt(Integer::intValue)
                .sum();
        int targetCalorie = userFoodIntakes.getFirst().getUser().getDailyCalorie(); // TODO EMPTY LIST
        int differenceCalorie = targetCalorie - actualCalorie;
        var isDailyCalorieNormExceeded = differenceCalorie < 0;
        return new DailyCalorieCheckDto(actualCalorie, targetCalorie, differenceCalorie, isDailyCalorieNormExceeded);
    }

    public List<HistoryReportDto> createHistoryReport(Long userId) {
        User user = userDao.findById(userId).orElseThrow();

        Map<LocalDate, Map<IntakeType, List<Meal>>> dateIntakeTypeMealMap = new LinkedHashMap<>();
        user.getFoodIntakes().forEach(foodIntake -> {
            var date = foodIntake.getDate();
            var intakeType = foodIntake.getType();
            var meal = foodIntake.getMeal();

            if (dateIntakeTypeMealMap.containsKey(date)) {
                Map<IntakeType, List<Meal>> intakeTypeMealMap = dateIntakeTypeMealMap.get(date);
                if (intakeTypeMealMap.containsKey(intakeType)) {
                    List<Meal> meals = intakeTypeMealMap.get(intakeType);
                    meals.add(meal);
                } else {
                    List<Meal> meals = new ArrayList<>();
                    meals.add(meal);
                    intakeTypeMealMap.put(intakeType, meals);
                }
            } else {
                Map<IntakeType, List<Meal>> intakeTypeMealMap = new EnumMap<>(IntakeType.class);
                List<Meal> meals = new ArrayList<>();
                meals.add(meal);
                intakeTypeMealMap.put(intakeType, meals);
                dateIntakeTypeMealMap.put(date, intakeTypeMealMap);
            }
        });

        return dateIntakeTypeMealMap.entrySet()
                .stream()
                .map(dateIntakeTypeMealES -> {

                    var date = dateIntakeTypeMealES.getKey();
                    Map<IntakeType, HistoryReportDto.IntakeTypeMealDto> intakeTypeMealMap = new EnumMap<>(IntakeType.class);
                    dateIntakeTypeMealES.getValue().forEach((intakeType, value) -> {

                        var totalCalorie = value
                                .stream()
                                .map(Meal::getNutrition)
                                .mapToInt(Integer::intValue)
                                .sum();
                        Map<String, Integer> foodMap = value
                                .stream()
                                .collect(Collectors.groupingBy(Meal::getName, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
                        var foodList = foodMap.entrySet()
                                .stream()
                                .map(es -> new HistoryReportDto.IntakeTypeMealDto.FoodDto(es.getValue(), es.getKey()))
                                .toList();
                        var intakeTypeMealDto = new HistoryReportDto.IntakeTypeMealDto(totalCalorie, foodList);
                        intakeTypeMealMap.put(intakeType, intakeTypeMealDto);
                    });

                    return new HistoryReportDto(date, intakeTypeMealMap);

                }).toList();
    }
}
