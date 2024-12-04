CREATE TABLE IF NOT EXISTS salaries (
    id SERIAL PRIMARY KEY,
    grade TEXT NOT NULL,
    minSalary INT NOT NULL,
    maxSalary INT NOT NULL
);