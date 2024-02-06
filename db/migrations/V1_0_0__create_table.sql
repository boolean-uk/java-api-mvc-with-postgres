CREATE TABLE IF NOT EXISTS employees (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    jobName TEXT,
    salaryGrade TEXT,
    department TEXT
);