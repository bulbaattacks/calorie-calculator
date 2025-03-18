CREATE SCHEMA IF NOT EXISTS calorie;

CREATE TABLE IF NOT EXISTS calorie.user_data (
    id           bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name         varchar(255) NOT NULL, -- Имя
    email        varchar(320) NOT NULL, -- Email
    age          integer NOT NULL, -- Возраст
    weight       integer NOT NULL, -- Вес
    height       integer NOT NULL, -- Рост
    weight_aim   varchar(16) NOT NULL, -- Цель (Похудение, Поддержание, Набор массы)
    CONSTRAINT chk_weight_aim CHECK (weight_aim IN ('loss', 'maintenance', 'gain'))
);

CREATE TABLE IF NOT EXISTS calorie.meal (
    id           bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name         varchar(255) NOT NULL, -- Название
    calorie_per_meal        varchar(320) NOT NULL, -- Количество калорий на порцию
    protein      integer NOT NULL, -- Белки
    fat     integer NOT NULL, -- Жиры
    carb     integer NOT NULL -- Углеводы
);

CREATE TABLE IF NOT EXISTS calorie.food_intake (
    intake_date  date NOT NULL,
    user_id      bigint NOT NULL,
    meal_id      bigint NOT NULL,
    quantity     integer NOT NULL,
    CONSTRAINT fk_user_data FOREIGN KEY (user_id)
        REFERENCES calorie.user_data(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_meal FOREIGN KEY (meal_id)
            REFERENCES calorie.meal(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    PRIMARY KEY (intake_date, user_id, meal_id)
);