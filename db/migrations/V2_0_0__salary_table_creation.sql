CREATE TABLE Salaries (
    id SERIAL PRIMARY KEY,
    grade VARCHAR(2) NOT NULL,
    minSalary INTEGER NOT NULL,
    maxSalary INTEGER NOT NULL
);