package ru.systems.calorie_calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.systems.calorie_calculator.dao.FoodIntakeDao;
import ru.systems.calorie_calculator.dao.MealDao;
import ru.systems.calorie_calculator.dao.UserDao;
import ru.systems.calorie_calculator.dto.FoodIntakeDto;
import ru.systems.calorie_calculator.entity.FoodIntake;
import ru.systems.calorie_calculator.entity.Meal;
import ru.systems.calorie_calculator.entity.User;
import ru.systems.calorie_calculator.exception.MealNotFoundException;
import ru.systems.calorie_calculator.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodIntakeService {

    private final UserDao userDao;
    private final MealDao mealDao;
    private final FoodIntakeDao foodIntakeDao;

    public void saveFoodIntake(FoodIntakeDto dto) {
        User user = userDao.findById(dto.getUserId()).orElseThrow(UserNotFoundException::new);
        Map<Long, Meal> idMealMap = mealDao.findAllById(dto.getMealIds())
                .stream()
                .collect(Collectors.toMap(Meal::getId, Function.identity()));
        if (Collections.disjoint(idMealMap.keySet(), dto.getMealIds())) {
            throw new MealNotFoundException();
        }

        LocalDate intakeDate = LocalDate.now();
        List<FoodIntake> intakes = new ArrayList<>();
        dto.getMealIds().forEach(mealId -> {
            var meal = idMealMap.get(mealId);
            var foodIntake = new FoodIntake(null, intakeDate, dto.getIntakeType(), user, meal);
            intakes.add(foodIntake);
        });
        foodIntakeDao.saveAll(intakes);
    }
}
