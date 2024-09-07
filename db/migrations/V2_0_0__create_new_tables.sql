CREATE TABLE IF NOT EXISTS salaries (
    id serial PRIMARY KEY,
    grade TEXT NOT NULL,
    minSalary INTEGER NOT NULL,
    maxSalary INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS departments (
    id serial PRIMARY KEY,
    name TEXT NOT NULL,
    location TEXT NOT NULL
);