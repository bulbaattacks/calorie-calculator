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
public class FoodIntake {

    @EmbeddedId
    private FoodIntakeId id;

    @Embeddable
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FoodIntakeId {
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

    public static FoodIntake create(LocalDate date,
                                    IntakeType type,
                                    User user,
                                    Meal meal) {
        var entity = new FoodIntake();
        entity.setId(new FoodIntakeId(date, type, user, meal));
        return entity;
    }
}
