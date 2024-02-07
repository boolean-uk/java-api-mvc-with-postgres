CREATE TABLE Employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    jobName VARCHAR(250) NOT NULL,
    salaryGrade VARCHAR(10) NOT NULL,
    department VARCHAR(50) NOT NULL
);