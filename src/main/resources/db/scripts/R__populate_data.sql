INSERT INTO calorie.user_data (name, email, age, weight, height, weight_aim)
    VALUES ('Arina', 'arina@mail.ru', 30, 60, 166, 'LOSS');
INSERT INTO calorie.user_data (name, email, age, weight, height, weight_aim)
    VALUES ('Jack', 'jack@mail.ru', 5, 16, 32, 'GAIN');

INSERT INTO calorie.meal (name, nutrition, protein, fat, carb)
    VALUES ('banana', '70', 2, 6, 5);
INSERT INTO calorie.meal (name, nutrition, protein, fat, carb)
    VALUES ('apple', '40', 3, 2, 1);

INSERT INTO calorie.food_intake (intake_date, intake_type, user_id, meal_id)
    VALUES ('2025-03-19', 'BREAKFAST', 1, 1);
INSERT INTO calorie.food_intake (intake_date, intake_type, user_id, meal_id)
    VALUES ('2025-03-19', 'BREAKFAST', 2, 2);