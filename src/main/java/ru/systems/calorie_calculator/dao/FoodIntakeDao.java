package ru.systems.calorie_calculator.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.systems.calorie_calculator.entity.FoodIntake;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface FoodIntakeDao extends JpaRepository<FoodIntake, Long> {

    @EntityGraph(attributePaths = {FoodIntake.Fields.user, FoodIntake.Fields.meal})
    List<FoodIntake> findAllByDateAndUserId(LocalDate date, Long userId);
}