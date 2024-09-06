CREATE TABLE IF NOT EXISTS salaries (
  id SERIAL PRIMARY KEY,
  grade TEXT,
  minSalary INTEGER,
  maxSalary INTEGER
);