
CREATE TABLE country (
                         code CHAR(3) PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         continent VARCHAR(50),
                         region VARCHAR(50),
                         surfacearea NUMERIC,
                         indepyear INT,
                         population INT,
                         localname VARCHAR(100),
                         governmentform VARCHAR(100),
                         capital INT,
                         code2 CHAR(2)
);
CREATE TABLE city (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      countrycode CHAR(3) NOT NULL REFERENCES country(code) ON DELETE CASCADE,
                      district VARCHAR(100),
                      population INT
);

CREATE TABLE сountryLanguage (
                                 countrycode CHAR(3) NOT NULL REFERENCES country(code) ON DELETE CASCADE,
                                 language VARCHAR(50) NOT NULL,
                                 isofficial BOOLEAN,
                                 percentage NUMERIC,
                                 PRIMARY KEY (countrycode, language)
);

INSERT INTO country (code, name, continent, region, surfacearea, indepyear, population, localname, governmentform, capital, code2)
VALUES
    ('FRA', 'France', 'Europe', 'Western Europe', 551695, 843, 67000000, 'France', 'Republic', NULL, 'FR'),
    ('USA', 'United States', 'North America', 'Northern America', 9372610, 1776, 331000000, 'United States', 'Federal Republic', NULL, 'US'),
    ('UKR', 'Ukraine', 'Europe', 'Eastern Europe', 603700, 1991, 41000000, 'Україна', 'Republic', NULL, 'UA'),
    ('GER', 'Germany', 'Europe', 'Western Europe', 357022, 1955, 83000000, 'Deutschland', 'Federal Republic', NULL, 'DE');


INSERT INTO city (name, countrycode, district, population)
VALUES
    ('Paris', 'FRA', 'Ile-de-France', 2148000),
    ('Lyon', 'FRA', 'Auvergne-Rhône-Alpes', 515695),
    ('New York', 'USA', 'New York', 8419600),
    ('Los Angeles', 'USA', 'California', 3980400),
    ('Kyiv', 'UKR', 'Kyiv', 2967000),
    ('Lviv', 'UKR', 'Lvivska', 721301),
    ('Berlin', 'GER', 'Berlin', 3769000),
    ('Hamburg', 'GER', 'Hamburg', 1841000);


INSERT INTO сountryLanguage (countrycode, language, isofficial, percentage)
VALUES
    ('FRA', 'French', TRUE, 96.0),
    ('USA', 'English', TRUE, 95.0),
    ('UKR', 'Ukrainian', TRUE, 90.0),
    ('UKR', 'Russian', FALSE, 29.0),
    ('GER', 'German', TRUE, 95.0),
    ('FRA', 'German', FALSE, 4.0);
