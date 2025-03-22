package ru.systems.calorie_calculator.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.systems.calorie_calculator.dao.MealDao;
import ru.systems.calorie_calculator.dto.MealDto;
import ru.systems.calorie_calculator.entity.Meal;

@Service
@RequiredArgsConstructor
public class MealService {

    private final ModelMapper mapper;
    private final MealDao dao;

    public MealDto saveMeal(MealDto dto) {
        var entity = mapper.map(dto, Meal.class);
        dao.save(entity);
        return mapper.map(entity, MealDto.class);
    }
}
