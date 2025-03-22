package ru.systems.calorie_calculator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import ru.systems.calorie_calculator.TestConfig;
import ru.systems.calorie_calculator.dao.FoodIntakeDao;
import ru.systems.calorie_calculator.dao.MealDao;
import ru.systems.calorie_calculator.dao.UserDao;
import ru.systems.calorie_calculator.dto.FoodIntakeDto;
import ru.systems.calorie_calculator.entity.User;
import ru.systems.calorie_calculator.exception.MealNotFoundException;
import ru.systems.calorie_calculator.exception.UserNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class FoodIntakeServiceTest {

    @Mock
    private UserDao userDao;
    @Mock
    private MealDao mealDao;
    @Mock
    private FoodIntakeDao foodIntakeDao;

    @InjectMocks
    private FoodIntakeService service;

    @Test
    void saveFoodIntake_throw_ex_user_not_found() {
        FoodIntakeDto dto = new FoodIntakeDto();
        Exception exception = assertThrows(UserNotFoundException.class, () -> service.saveFoodIntake(dto));
        String expected = UserNotFoundException.USER_NOT_FOUND;
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void saveFoodIntake_throw_ex_meal_not_found() {
        FoodIntakeDto dto = new FoodIntakeDto();
        Long idNotExist = 1L;
        dto.setMealIds(Set.of(idNotExist));

        when(userDao.findById(any()))
                .thenReturn(Optional.of(new User()));

        Exception exception = assertThrows(MealNotFoundException.class, () -> service.saveFoodIntake(dto));
        String expected = MealNotFoundException.MEAL_NOT_FOUND;
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }
}
