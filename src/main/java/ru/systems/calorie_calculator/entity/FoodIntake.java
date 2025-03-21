package ru.systems.calorie_calculator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.systems.calorie_calculator.dto.IntakeType;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "food_intake")
@AllArgsConstructor
@NoArgsConstructor
public class FoodIntake {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private IntakeType type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;
}
