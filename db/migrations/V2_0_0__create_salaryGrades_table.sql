CREATE TABLE IF NOT EXISTS SalaryGrades(
    id SERIAL PRIMARY KEY,
    grade TEXT,
    minSalary INT,
    maxSalary INT
)