CREATE TABLE IF NOT EXISTS Employees (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    job_name TEXT NOT NULL,
    salary_grade INT NOT NULL,
    department TEXT NOT NULL
);