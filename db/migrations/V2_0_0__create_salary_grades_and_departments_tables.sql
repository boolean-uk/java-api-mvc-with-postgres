 CREATE TABLE IF NOT EXISTS salary_grades (
    id SERIAL PRIMARY KEY,
    grade TEXT,
    min_salary INTEGER,
    max_salary INTEGER
);

 CREATE TABLE IF NOT EXISTS departments (
    id SERIAL PRIMARY KEY,
    name TEXT,
    location TEXT
);