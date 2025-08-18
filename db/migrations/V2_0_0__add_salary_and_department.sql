DELETE FROM employees;

CREATE TABLE salaries(
    id SERIAL PRIMARY KEY,
    grade TEXT NOT NULL,
    minSalary INT NOT NULL,
    maxSalary INT NOT NULL
);
CREATE TABLE departments(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    location TEXT NOT NULL
);

ALTER TABLE employees
RENAME salaryGrade TO salaryId;
ALTER TABLE employees
RENAME department TO departmentId;

--Weird conversion, the table should be empty though
ALTER TABLE employees
ALTER COLUMN salaryId TYPE INT USING salaryId::INT;
ALTER TABLE employees
ALTER COLUMN departmentId TYPE INT USING departmentId::INT;

ALTER TABLE employees
ADD CONSTRAINT fk_salaries FOREIGN KEY (salaryId) REFERENCES salaries(id);
ALTER TABLE employees
ADD CONSTRAINT fk_departments FOREIGN KEY (departmentId) REFERENCES departments(id);