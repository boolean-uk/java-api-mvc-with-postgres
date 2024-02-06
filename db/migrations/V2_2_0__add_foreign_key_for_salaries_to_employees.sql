ALTER TABLE employees
ADD COLUMN salary_id INT;

ALTER TABLE employees
ADD CONSTRAINT fk_salary FOREIGN KEY(salary_id) REFERENCES salaries(id);