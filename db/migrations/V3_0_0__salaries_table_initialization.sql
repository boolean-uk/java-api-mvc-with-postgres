CREATE TABLE IF NOT EXISTS salaries(
    id SERIAL PRIMARY KEY,
    grade TEXT,
    min_salary INTEGER,
    max_salary INTEGER
);