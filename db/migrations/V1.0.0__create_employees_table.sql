CREATE TABLE IF NOT EXISTS Employees (
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    jobName VARCHAR(50) NOT NULL,
    salaryGrade VARCHAR(10) NOT NULL,
    department VARCHAR(50)
)

