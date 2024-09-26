CREATE TABLE IF NOT EXISTS Salaries (
    id SERIAL PRIMARY KEY,
    grade TEXT,
    min_salary INT,
    max_salary INT
);