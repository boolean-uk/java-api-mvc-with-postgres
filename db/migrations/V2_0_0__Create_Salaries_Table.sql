CREATE TABLE IF NOT EXISTS salaries(
    id SERIAL PRIMARY KEY,
    grade TEXT NOT NULL UNIQUE,
    min_Salary INT NOT NULL,
    max_Salary INT NOT NULL
);