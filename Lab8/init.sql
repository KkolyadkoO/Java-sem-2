CREATE DATABASE Lab8;

CREATE TABLE students (
                          id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          "group" VARCHAR(50) NOT NULL,
                          avg_grade NUMERIC(3,2) NOT NULL
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       login VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL DEFAULT 'user'
);

INSERT INTO students (name, "group", avg_grade) VALUES
                                                    ('Иван Иванов', 'ИКБО-01-21', 4.2),
                                                    ('Мария Петрова', 'ИКБО-02-21', 3.8),
                                                    ('Алексей Сидоров', 'ИКБО-03-21', 4.7),
                                                    ('Екатерина Смирнова', 'ИКБО-04-21', 4.5),
                                                    ('Дмитрий Павлов', 'ИКБО-05-21', 3.9),
                                                    ('Анна Кузнецова', 'ИКБО-06-21', 4.1),
                                                    ('Константин Орлов', 'ИКБО-07-21', 4.8),
                                                    ('Светлана Романова', 'ИКБО-08-21', 4.0),
                                                    ('Фёдор Беляев', 'ИКБО-09-21', 3.6),
                                                    ('Юлия Чернова', 'ИКБО-10-21', 4.3);