
CREATE TABLE IF NOT EXISTS employees(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    job_name TEXT NOT NULL,
    salary_grade TEXT NOT NULL,
    department TEXT NOT NULL
);