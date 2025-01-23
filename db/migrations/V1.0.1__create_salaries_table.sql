CREATE TABLE IF NOT EXISTS salaries (
    id SERIAL PRIMARY KEY,
    grade VARCHAR(255) NOT NULL,
    minSalary INT NOT NULL,
    maxSalary INT NOT NULL
)