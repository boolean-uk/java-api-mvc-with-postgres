CREATE TABLE IF NOT EXISTS salaries (
    id SERIAL PRIMARY KEY,
    salary_grade INT NOT NULL,
    min_salary INT NOT NULL,
    max_salary INT NOT NULL
);


