CREATE TABLE IF NOT EXISTS salaries(
    id SERIAL,
    grade TEXT PRIMARY KEY NOT NULL UNIQUE,
    min_Salary INT NOT NULL,
    max_Salary INT NOT NULL
);