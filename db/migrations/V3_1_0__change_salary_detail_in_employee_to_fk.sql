ALTER TABLE employees
DROP COLUMN salaryGrade,
ADD COLUMN salary_id INT;

ALTER TABLE employees
ADD CONSTRAINT fk_salary_id FOREIGN KEY (salary_id) REFERENCES salaries (id);