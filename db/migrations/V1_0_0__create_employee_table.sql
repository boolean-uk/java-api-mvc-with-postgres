CREATE TABLE IF NOT EXISTS Employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    jobName VARCHAR(255),
    salaryGrade VARCHAR(10),
    department VARCHAR(50)
);