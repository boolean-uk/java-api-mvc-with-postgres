
CREATE TABLE IF NOT EXISTS employees(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    jobName TEXT NOT NULL,
    salaryGrade TEXT,
    department TEXT
);