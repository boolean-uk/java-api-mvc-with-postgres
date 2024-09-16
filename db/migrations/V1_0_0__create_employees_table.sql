CREATE TABLE IF NOT EXISTS employees (
    id serial PRIMARY KEY,
    name TEXT not null,
    jobName TEXT not null,
    salaryGrade TEXT,
    department TEXT not null
);