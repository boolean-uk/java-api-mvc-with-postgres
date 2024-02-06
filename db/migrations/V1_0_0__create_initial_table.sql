CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    name TEXT,
    jobName TEXT,
    salaryGrade VARCHAR(10),
    department VARCHAR(10)
);
