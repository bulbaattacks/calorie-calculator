package ru.systems.calorie_calculator.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_data")
public class User {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Integer weight;
    private Integer height;
    private String weightAim;
    private Integer dailyCalorie;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    @OrderBy("date ASC")
    private List<FoodIntake> foodIntakes;
}
