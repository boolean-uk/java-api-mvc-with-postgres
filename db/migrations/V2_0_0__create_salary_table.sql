CREATE TABLE IF NOT EXISTS salaries (
    id SERIAL PRIMARY KEY,
    grade TEXT NOT NULL,
    min_salary TEXT NOT NULL,
    max_salary TEXT NOT NULL
)