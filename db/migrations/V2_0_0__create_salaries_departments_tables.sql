CREATE TABLE IF NOT EXISTS Salaries (
    id SERIAL PRIMARY KEY,
    grade VARCHAR(10),
    minSalary INTEGER,
    maxSalary INTEGER
);

CREATE TABLE IF NOT EXISTS Departments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    location VARCHAR(255)
);