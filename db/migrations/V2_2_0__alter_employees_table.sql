TRUNCATE employees;

ALTER TABLE employees DROP COLUMN department;
ALTER TABLE employees DROP COLUMN salaryGrade;

ALTER TABLE employees
ADD COLUMN department_id INT REFERENCES departments(id),
ADD COLUMN salary_grade_id INT REFERENCES salaries(id);