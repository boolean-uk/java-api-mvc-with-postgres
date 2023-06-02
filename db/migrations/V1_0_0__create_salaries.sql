CREATE TABLE Salaries (
    id SERIAL PRIMARY KEY,
    grade TEXT NOT NULL,
    min_salary INTEGER NOT NULL,
    max_salary INTEGER NOT NULL
);