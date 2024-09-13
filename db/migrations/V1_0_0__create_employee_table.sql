CREATE TABLE IF NOT EXISTS Employees (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    jobName TEXT,
    salaryGrade TEXT,
    department TEXT
)