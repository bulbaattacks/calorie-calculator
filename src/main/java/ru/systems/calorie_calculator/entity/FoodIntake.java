package ru.systems.calorie_calculator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "food_intake")
public class FoodIntake {

    @EmbeddedId
    private FoodIntakeId id;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FoodIntakeId {
        private LocalDate intakeDate;
        private String intakeType;
        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;
        @ManyToOne
        @JoinColumn(name = "meal_id")
        private Meal meal;
    }

    public static FoodIntake create(LocalDate intakeDate,
                                    String intakeType,
                                    User user,
                                    Meal meal) {
        var entity = new FoodIntake();
        entity.setId(new FoodIntakeId(intakeDate, intakeType, user, meal));
        return entity;
    }
}
