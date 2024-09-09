CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    jobName TEXT NOT NULL,
    salaryGrade TEXT NOT NULL,
    department TEXT NOT NULL
);
