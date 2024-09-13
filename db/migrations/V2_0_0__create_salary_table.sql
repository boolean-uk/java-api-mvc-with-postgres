CREATE TABLE IF NOT EXISTS Salaries (
    id SERIAL PRIMARY KEY,
    grade TEXT NOT NULL,
    minSalary INTEGER,
    maxSalary INTEGER
)