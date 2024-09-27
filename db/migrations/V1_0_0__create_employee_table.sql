CREATE TABLE IF NOT EXISTS Employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250),
    jobName VARCHAR(250),
    salaryGrade VARCHAR(10),
    department VARCHAR(50)
);