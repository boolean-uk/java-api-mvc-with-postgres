ALTER TABLE employees
DROP COLUMN salaryGrade,
DROP COLUMN department;

ALTER TABLE employees
ADD COLUMN salary_id INTEGER,
ADD COLUMN department_id INTEGER;

ALTER TABLE employees
ADD FOREIGN KEY (salary_id)
    REFERENCES salaries(id);

ALTER TABLE employees
ADD FOREIGN KEY (department_id)
    REFERENCES departments(id);
