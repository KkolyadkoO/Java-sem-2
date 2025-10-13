CREATE DATABASE Lab6;

CREATE TABLE students (
                          id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          "group" VARCHAR(50) NOT NULL,
                          avg_grade NUMERIC(3,2) NOT NULL
);