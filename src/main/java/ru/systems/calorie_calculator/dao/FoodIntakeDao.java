package ru.systems.calorie_calculator.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.systems.calorie_calculator.entity.FoodIntake;

@Repository
public interface FoodIntakeDao extends JpaRepository<FoodIntake, Long> {
}