CREATE DATABASE lab4;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS publishers CASCADE;

CREATE TABLE publishers (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            country VARCHAR(100)
);

CREATE TABLE authors (
                         id SERIAL PRIMARY KEY,
                         full_name VARCHAR(100) NOT NULL,
                         birth_date DATE
);

CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(150) NOT NULL,
                       author_id INT REFERENCES authors(id) ON DELETE CASCADE,
                       publisher_id INT REFERENCES publishers(id) ON DELETE CASCADE,
                       publish_year INT
);

-- Издательства
INSERT INTO publishers (name, country) VALUES
                                           ('Penguin Books', 'UK'),
                                           ('HarperCollins', 'USA'),
                                           ('Drofa', 'Russia');

-- Авторы
INSERT INTO authors (full_name, birth_date) VALUES
                                                ('George Orwell', '1903-06-25'),
                                                ('Aldous Huxley', '1894-07-26'),
                                                ('Fyodor Dostoevsky', '1821-11-11'),
                                                ('Leo Tolstoy', '1828-09-09'),
                                                ('Stephen King', '1947-09-21');

-- Книги
INSERT INTO books (title, author_id, publisher_id, publish_year) VALUES
                                                                     ('1984', 1, 1, 2021),
                                                                     ('Animal Farm', 1, 1, 2019),
                                                                     ('Brave New World', 2, 2, 2018),
                                                                     ('Crime and Punishment', 3, 3, 1871),
                                                                     ('The Brothers Karamazov', 3, 3, 1880),
                                                                     ('War and Peace', 4, 3, 1869),
                                                                     ('The Shining', 5, 2, 1977),
                                                                     ('It', 5, 2, 1986),
                                                                     ('Fairy Tale', 5, 2, 2022),
                                                                     ('Billy Summers', 5, 2, 2021);
