INSERT INTO calorie.user_data (name, email, age, weight, height, weight_aim, daily_calorie)
    VALUES ('Arina', 'arina@mail.ru', 30, 60, 166, 'LOSS', 6666);

INSERT INTO calorie.meal (name, nutrition, protein, fat, carb)
    VALUES ('banana', '70', 2, 6, 5);
INSERT INTO calorie.meal (name, nutrition, protein, fat, carb)
    VALUES ('apple', '40', 3, 2, 1);

INSERT INTO calorie.food_intake (date, type, user_id, meal_id)
    VALUES ('2025-03-19', 'BREAKFAST', 1, 1);
INSERT INTO calorie.food_intake (date, type, user_id, meal_id)
    VALUES ('2025-03-19', 'BREAKFAST', 1, 1);
INSERT INTO calorie.food_intake (date, type, user_id, meal_id)
    VALUES ('2025-03-19', 'BREAKFAST', 1, 2);

INSERT INTO calorie.food_intake (date, type, user_id, meal_id)
    VALUES (CURRENT_DATE, 'BREAKFAST', 1, 2);

INSERT INTO calorie.food_intake (date, type, user_id, meal_id)
    VALUES (CURRENT_DATE, 'LUNCH', 1, 1);
INSERT INTO calorie.food_intake (date, type, user_id, meal_id)
    VALUES (CURRENT_DATE, 'LUNCH', 1, 2);
INSERT INTO calorie.food_intake (date, type, user_id, meal_id)
    VALUES (CURRENT_DATE, 'LUNCH', 1, 2);

INSERT INTO calorie.food_intake (date, type, user_id, meal_id)
    VALUES (CURRENT_DATE, 'DINNER', 1, 2);
INSERT INTO calorie.food_intake (date, type, user_id, meal_id)
    VALUES (CURRENT_DATE, 'DINNER', 1, 2);
INSERT INTO calorie.food_intake (date, type, user_id, meal_id)
    VALUES (CURRENT_DATE, 'DINNER', 1, 2);