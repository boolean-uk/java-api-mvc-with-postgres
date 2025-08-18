CREATE TABLE IF NOT EXISTS SalaryGrades(
    id serial PRIMARY KEY,
    grade TEXT,
    minSalary INTEGER,
    maxSalary INTEGER
);

CREATE TABLE IF NOT EXISTS Departments(
    id serial PRIMARY KEY,
    name TEXT,
    location TEXT
);
