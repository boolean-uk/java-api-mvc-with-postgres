CREATE TABLE salaries(
    id SERIAL PRIMARY KEY,
    grade TEXT,
    min_salary INTEGER,
    max_salary INTEGER
);

CREATE TABLE departments(
    id SERIAL PRIMARY KEY,
    name TEXT,
    location TEXT
);