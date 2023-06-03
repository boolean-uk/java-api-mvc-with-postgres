CREATE TABLE If NOT EXISTS employee
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    jobName TEXT,
    salaryGrade TEXT,
    department TEXT
)