CREATE TABLE IF NOT EXISTS Salaries (
    id serial PRIMARY KEY,
    grade TEXT NOT NULL,
    minSalary INT NOT NULL,
    maxSalary INT NOT NULL
);