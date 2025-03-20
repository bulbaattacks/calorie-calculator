package ru.systems.calorie_calculator.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.systems.calorie_calculator.dao.UserDao;
import ru.systems.calorie_calculator.dto.RequestUserDto;
import ru.systems.calorie_calculator.dto.ResponseUserDto;
import ru.systems.calorie_calculator.entity.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper mapper;
    private final UserDao dao;

    public ResponseUserDto saveUser(RequestUserDto dto) {
        int dailyCalorie = calculateDailyCalorie(dto.getWeight(), dto.getHeight(), dto.getAge());
        var entity = mapper.map(dto, User.class);
        entity.setDailyCalorie(dailyCalorie);
        dao.save(entity);
        return mapper.map(entity, ResponseUserDto.class);
    }

    private int calculateDailyCalorie(final int weight, final int height, final int age) {
        return (int) (10 * weight + 6.25 * height - 5 * age + 5);
    }
}
