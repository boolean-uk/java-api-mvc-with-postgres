CREATE TABLE IF NOT EXISTS Salaries(
    id SERIAL PRIMARY KEY,
    grade VARCHAR(10),
    min_salary INT NOT NULL,
    max_salary INT NOT NULL
);
CREATE TABLE IF NOT EXISTS Departments(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    location VARCHAR(250) NOT NULL
);
