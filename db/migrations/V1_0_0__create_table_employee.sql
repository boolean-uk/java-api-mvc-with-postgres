CREATE TABLE IF NOT EXISTS employees (
    id serial PRIMARY KEY,
    name TEXT NOT NULL,
    jobName TEXT NOT NULL,
    salaryGrade TEXT NOT NULL,
    department TEXT NOT NULL
);