package ru.systems.calorie_calculator.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.systems.calorie_calculator.entity.FoodIntake;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodIntakeDao extends JpaRepository<FoodIntake, Long> {

    @Query("SELECT fi FROM FoodIntake fi WHERE fi.id.user.id = :id AND fi.id.date = :date")
    List<FoodIntake> findByUserIdAndDate(@Param("id") Long id, @Param("date") LocalDate date);
}