CREATE TABLE IF NOT EXISTS salaries (
    id SERIAL PRIMARY KEY,
    salary_grade TEXT,
    min_salary INT,
    max_salary INT
);