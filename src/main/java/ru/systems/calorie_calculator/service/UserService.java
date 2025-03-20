package ru.systems.calorie_calculator.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.systems.calorie_calculator.dao.UserDao;
import ru.systems.calorie_calculator.dto.UserDto;
import ru.systems.calorie_calculator.entity.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper mapper;
    private final UserDao dao;

    public void saveUser(UserDto dto) {
        var entity = mapper.map(dto, User.class);
        dao.save(entity);
    }
}
