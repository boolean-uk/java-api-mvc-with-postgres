CREATE TABLE IF NOT EXISTS employees(
    id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    jobName VARCHAR(250),
    salaryGrade VARCHAR(10),
    department VARCHAR(50)
);