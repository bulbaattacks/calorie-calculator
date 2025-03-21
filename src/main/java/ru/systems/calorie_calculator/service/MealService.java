package ru.systems.calorie_calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.systems.calorie_calculator.dao.MealDao;
import ru.systems.calorie_calculator.dto.MealDto;
import ru.systems.calorie_calculator.entity.Meal;

@Service
@RequiredArgsConstructor
public class MealService {

    private final ObjectMapper mapper;
    private final MealDao dao;

    public void saveMeal(MealDto dto) {
        var entity = mapper.convertValue(dto, Meal.class);
        dao.save(entity);
    }
}
