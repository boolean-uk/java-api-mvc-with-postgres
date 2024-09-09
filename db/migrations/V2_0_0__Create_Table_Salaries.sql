CREATE TABLE IF NOT EXISTS salaries(
    id SERIAL PRIMARY KEY,
    min_salary INT NOT NULL,
    max_salary INT NOT NULL
);