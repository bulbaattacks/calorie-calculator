package ru.systems.calorie_calculator.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.systems.calorie_calculator.entity.FoodIntake;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface FoodIntakeDao extends JpaRepository<FoodIntake, Long> {

    @Query("SELECT fi FROM FoodIntake fi WHERE fi.id.user.id = :userId AND fi.id.date = :date")
    List<FoodIntake> findAllByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT fi FROM FoodIntake fi WHERE fi.id.user.id = :userId")
    List<FoodIntake> findAllByUserId(@Param("userId") Long userId);
}