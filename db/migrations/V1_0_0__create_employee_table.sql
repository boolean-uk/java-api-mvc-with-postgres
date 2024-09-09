CREATE TABLE IF NOT EXISTS employees (
    id serial PRIMARY KEY,
    name text NOT NULL,
    jobName text,
    salaryGrade text,
    department text
);