CREATE TABLE IF NOT EXISTS Salaries (
    id SERIAL PRIMARY KEY,
    grade INT NOT NULL,
    minSalary INT NOT NULL,
    maxSalary INT NOT NULL
);