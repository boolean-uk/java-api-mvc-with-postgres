ALTER TABLE employees
ADD COLUMN salary_id INT NOT NULL;

ALTER TABLE employees
ADD CONSTRAINT fk_salary FOREIGN KEY(salary_id) REFERENCES salaries(id);