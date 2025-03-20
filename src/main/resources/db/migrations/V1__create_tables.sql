CREATE TABLE IF NOT EXISTS calorie.user_data (
    id            bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name          varchar(255) NOT NULL, -- Имя
    email         varchar(320) NOT NULL, -- Email
    age           integer NOT NULL, -- Возраст
    weight        integer NOT NULL, -- Вес
    height        integer NOT NULL, -- Рост
    weight_aim    varchar(16) NOT NULL, -- Цель (Похудение, Поддержание, Набор массы)
    daily_calorie integer  NOT NULL,
    CONSTRAINT chk_weight_aim CHECK (weight_aim IN ('LOSS', 'MAINTENANCE', 'GAIN'))
);

CREATE TABLE IF NOT EXISTS calorie.meal (
    id                  bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name                varchar(255) NOT NULL, -- Название
    nutrition           integer NOT NULL, -- Количество калорий на 100 g
    protein             integer NOT NULL, -- Белки
    fat                 integer NOT NULL, -- Жиры
    carb                integer NOT NULL -- Углеводы
);

CREATE TABLE IF NOT EXISTS calorie.food_intake (
    date        date NOT NULL,
    type        varchar(16) NOT NULL,
    user_id     bigint NOT NULL,
    meal_id     bigint NOT NULL,
    CONSTRAINT chk_intake_type CHECK (type IN ('BREAKFAST', 'LUNCH', 'DINNER')),
    CONSTRAINT fk_user_data FOREIGN KEY (user_id)
        REFERENCES calorie.user_data(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_meal FOREIGN KEY (meal_id)
            REFERENCES calorie.meal(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    PRIMARY KEY (date, type, user_id, meal_id)
);