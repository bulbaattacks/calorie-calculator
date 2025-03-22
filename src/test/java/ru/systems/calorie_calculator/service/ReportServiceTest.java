package ru.systems.calorie_calculator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import ru.systems.calorie_calculator.TestConfig;
import ru.systems.calorie_calculator.dao.FoodIntakeDao;
import ru.systems.calorie_calculator.dao.UserDao;
import ru.systems.calorie_calculator.dto.IntakeType;
import ru.systems.calorie_calculator.dto.report.DailyCalorieCheckDto;
import ru.systems.calorie_calculator.dto.report.DailyReportDto;
import ru.systems.calorie_calculator.dto.report.HistoryReportDto;
import ru.systems.calorie_calculator.entity.FoodIntake;
import ru.systems.calorie_calculator.entity.Meal;
import ru.systems.calorie_calculator.entity.User;
import ru.systems.calorie_calculator.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private UserDao userDao;
    @Mock
    private FoodIntakeDao foodIntakeDao;

    @InjectMocks
    private ReportService service;

    @Test
    void calculateDailyReport_throw_ex() {
        Long userId = 1L;
        Exception exception = assertThrows(UserNotFoundException.class, () -> service.calculateDailyReport(userId));
        String expected = UserNotFoundException.USER_NOT_FOUND;
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void calculateDailyReport_return_DailyReportDto() {
        Long userId = 1L;

        Meal meal1 = createMeal(100);
        Meal meal2 = createMeal(200);
        Meal meal3 = createMeal(300);

        FoodIntake fi1 = createFoodIntake(IntakeType.BREAKFAST, meal1);
        FoodIntake fi2 = createFoodIntake(IntakeType.LUNCH, meal2);
        FoodIntake fi3 = createFoodIntake(IntakeType.DINNER, meal3);

        List<FoodIntake> userFoodIntakes = List.of(fi1, fi2, fi3);

        when(userDao.existsById(userId)).thenReturn(true);
        when(foodIntakeDao.findAllByDateAndUserId(any(), eq(userId)))
                .thenReturn(userFoodIntakes);

        DailyReportDto resultDto = service.calculateDailyReport(userId);

        assertNotNull(resultDto);
        assertEquals(3, resultDto.caloriePerIntake().size());
        assertEquals(meal1.getNutrition(), resultDto.caloriePerIntake().get(IntakeType.BREAKFAST));
        assertEquals(meal2.getNutrition(), resultDto.caloriePerIntake().get(IntakeType.LUNCH));
        assertEquals(meal3.getNutrition(), resultDto.caloriePerIntake().get(IntakeType.DINNER));

        int expectedTotalCalorie = Stream.of(meal1, meal2, meal3)
                .map(Meal::getNutrition)
                .reduce(0, Integer::sum);
        assertEquals(expectedTotalCalorie, resultDto.totalCalorie());
    }

    @Test
    void calculateCalorieReminderReport_throw_ex() {
        Long userId = 1L;
        Exception exception = assertThrows(UserNotFoundException.class, () -> service.calculateCalorieReminderReport(userId));
        String expected = UserNotFoundException.USER_NOT_FOUND;
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void calculateCalorieReminderReport_return_DailyCalorieCheckDto_not_exceeded() {
        Long userId = 1L;

        User user = new User();
        user.setDailyCalorie(2000);

        Meal meal1 = createMeal(100);
        Meal meal2 = createMeal(200);
        Meal meal3 = createMeal(300);

        FoodIntake fi1 = createFoodIntake(IntakeType.BREAKFAST, meal1);
        FoodIntake fi2 = createFoodIntake(IntakeType.LUNCH, meal2);
        FoodIntake fi3 = createFoodIntake(IntakeType.DINNER, meal3);

        List<FoodIntake> userFoodIntakes = List.of(fi1, fi2, fi3);

        when(userDao.findById(userId))
                .thenReturn(Optional.of(user));
        when(foodIntakeDao.findAllByDateAndUserId(any(), eq(userId)))
                .thenReturn(userFoodIntakes);

        DailyCalorieCheckDto resultDto = service.calculateCalorieReminderReport(userId);

        assertNotNull(resultDto);

        int expectedActualCalorie = Stream.of(meal1, meal2, meal3)
                .map(Meal::getNutrition)
                .reduce(0, Integer::sum);
        assertEquals(expectedActualCalorie, resultDto.actualCalorie());
        assertEquals(user.getDailyCalorie(), resultDto.targetCalorie());

        int expectedDifferenceCalorie = user.getDailyCalorie() - expectedActualCalorie;
        assertEquals(expectedDifferenceCalorie, resultDto.differenceCalorie());
        assertFalse(resultDto.isDailyCalorieNormExceeded());
    }

    @Test
    void calculateCalorieReminderReport_return_DailyCalorieCheckDto_not_exceeded_diff_is_zero() {
        Long userId = 1L;

        User user = new User();
        user.setDailyCalorie(600);

        Meal meal1 = createMeal(100);
        Meal meal2 = createMeal(200);
        Meal meal3 = createMeal(300);

        FoodIntake fi1 = createFoodIntake(IntakeType.BREAKFAST, meal1);
        FoodIntake fi2 = createFoodIntake(IntakeType.LUNCH, meal2);
        FoodIntake fi3 = createFoodIntake(IntakeType.DINNER, meal3);

        List<FoodIntake> userFoodIntakes = List.of(fi1, fi2, fi3);

        when(userDao.findById(userId))
                .thenReturn(Optional.of(user));
        when(foodIntakeDao.findAllByDateAndUserId(any(), eq(userId)))
                .thenReturn(userFoodIntakes);

        DailyCalorieCheckDto resultDto = service.calculateCalorieReminderReport(userId);

        assertNotNull(resultDto);

        int expectedActualCalorie = Stream.of(meal1, meal2, meal3)
                .map(Meal::getNutrition)
                .reduce(0, Integer::sum);
        assertEquals(expectedActualCalorie, resultDto.actualCalorie());
        assertEquals(user.getDailyCalorie(), resultDto.targetCalorie());

        int expectedDifferenceCalorie = user.getDailyCalorie() - expectedActualCalorie;
        assertEquals(expectedDifferenceCalorie, resultDto.differenceCalorie());
        assertFalse(resultDto.isDailyCalorieNormExceeded());
    }

    @Test
    void calculateCalorieReminderReport_return_DailyCalorieCheckDto_exceeded() {
        Long userId = 1L;

        User user = new User();
        user.setDailyCalorie(2000);

        Meal meal1 = createMeal(1000);
        Meal meal2 = createMeal(2000);
        Meal meal3 = createMeal(3000);

        FoodIntake fi1 = createFoodIntake(IntakeType.BREAKFAST, meal1);
        FoodIntake fi2 = createFoodIntake(IntakeType.LUNCH, meal2);
        FoodIntake fi3 = createFoodIntake(IntakeType.DINNER, meal3);

        List<FoodIntake> userFoodIntakes = List.of(fi1, fi2, fi3);

        when(userDao.findById(userId))
                .thenReturn(Optional.of(user));
        when(foodIntakeDao.findAllByDateAndUserId(any(), eq(userId)))
                .thenReturn(userFoodIntakes);

        DailyCalorieCheckDto resultDto = service.calculateCalorieReminderReport(userId);

        assertNotNull(resultDto);

        int expectedActualCalorie = Stream.of(meal1, meal2, meal3)
                .map(Meal::getNutrition)
                .reduce(0, Integer::sum);
        assertEquals(expectedActualCalorie, resultDto.actualCalorie());
        assertEquals(user.getDailyCalorie(), resultDto.targetCalorie());

        int expectedDifferenceCalorie = user.getDailyCalorie() - expectedActualCalorie;
        assertEquals(expectedDifferenceCalorie, resultDto.differenceCalorie());
        assertTrue(resultDto.isDailyCalorieNormExceeded());
    }

    @Test
    void createHistoryReport_throw_ex() {
        Long userId = 1L;
        Exception exception = assertThrows(UserNotFoundException.class, () -> service.createHistoryReport(userId));
        String expected = UserNotFoundException.USER_NOT_FOUND;
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void createHistoryReport_return_historyReportDtos() {
        Long userId = 1L;
        User user = new User();

        Meal meal1 = createMeal("apple", 100);
        Meal meal2 = createMeal("banana", 200);
        Meal meal3 = createMeal("potato", 300);

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        FoodIntake fi1 = createFoodIntake(yesterday, IntakeType.BREAKFAST, meal1);
        FoodIntake fi2 = createFoodIntake(yesterday, IntakeType.BREAKFAST, meal1);

        FoodIntake fi3 = createFoodIntake(today, IntakeType.BREAKFAST, meal1);
        FoodIntake fi4 = createFoodIntake(today, IntakeType.LUNCH, meal2);
        FoodIntake fi5 = createFoodIntake(today, IntakeType.DINNER, meal3);


        List<FoodIntake> userFoodIntakes = List.of(fi1, fi2, fi3, fi4, fi5);
        user.setFoodIntakes(userFoodIntakes);

        when(userDao.findById(userId))
                .thenReturn(Optional.of(user));

        List<HistoryReportDto> resultDto = service.createHistoryReport(userId);

        assertEquals(2, resultDto.size());

        var yesterdayDto = resultDto.get(0);
        assertEquals(yesterday, yesterdayDto.foodIntakeDate());
        assertEquals(1, yesterdayDto.caloriePerIntake().size());

        assertEquals(200, yesterdayDto.caloriePerIntake().get(IntakeType.BREAKFAST).totalCalorie());
        assertEquals(1, yesterdayDto.caloriePerIntake().get(IntakeType.BREAKFAST).foodList().size());
        assertEquals(2, yesterdayDto.caloriePerIntake().get(IntakeType.BREAKFAST).foodList().get(0).quantity());
        assertEquals(meal1.getName(), yesterdayDto.caloriePerIntake().get(IntakeType.BREAKFAST).foodList().get(0).name());

        var todayDto = resultDto.get(1);
        assertEquals(today, todayDto.foodIntakeDate());
        assertEquals(3, todayDto.caloriePerIntake().size());

        assertEquals(100, todayDto.caloriePerIntake().get(IntakeType.BREAKFAST).totalCalorie());
        assertEquals(1, todayDto.caloriePerIntake().get(IntakeType.BREAKFAST).foodList().size());
        assertEquals(1, todayDto.caloriePerIntake().get(IntakeType.BREAKFAST).foodList().get(0).quantity());
        assertEquals(meal1.getName(), todayDto.caloriePerIntake().get(IntakeType.BREAKFAST).foodList().get(0).name());

        assertEquals(200, todayDto.caloriePerIntake().get(IntakeType.LUNCH).totalCalorie());
        assertEquals(1, todayDto.caloriePerIntake().get(IntakeType.LUNCH).foodList().size());
        assertEquals(1, todayDto.caloriePerIntake().get(IntakeType.LUNCH).foodList().get(0).quantity());
        assertEquals(meal2.getName(), todayDto.caloriePerIntake().get(IntakeType.LUNCH).foodList().get(0).name());

        assertEquals(300, todayDto.caloriePerIntake().get(IntakeType.DINNER).totalCalorie());
        assertEquals(1, todayDto.caloriePerIntake().get(IntakeType.DINNER).foodList().size());
        assertEquals(1, todayDto.caloriePerIntake().get(IntakeType.DINNER).foodList().get(0).quantity());
        assertEquals(meal3.getName(), todayDto.caloriePerIntake().get(IntakeType.DINNER).foodList().get(0).name());
    }

    private Meal createMeal(int nutrition) {
        Meal meal = new Meal();
        meal.setNutrition(nutrition);
        return meal;
    }

    private Meal createMeal(String name, int nutrition) {
        Meal meal = createMeal(nutrition);
        meal.setName(name);
        return meal;
    }

    private FoodIntake createFoodIntake(IntakeType intakeType, Meal meal) {
        FoodIntake foodIntake = new FoodIntake();
        foodIntake.setType(intakeType);
        foodIntake.setMeal(meal);
        return foodIntake;
    }

    private FoodIntake createFoodIntake(LocalDate date, IntakeType intakeType, Meal meal) {
        FoodIntake foodIntake = createFoodIntake(intakeType, meal);
        foodIntake.setDate(date);
        return foodIntake;
    }
}

