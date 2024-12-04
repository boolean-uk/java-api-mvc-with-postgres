ALTER TABLE employees
DROP COLUMN salary_grade,
DROP COLUMN department;

ALTER TABLE employees
ADD COLUMN salary_id int,
ADD COLUMN department_id int;

ALTER TABLE employees
ADD CONSTRAINT fk_salary_id FOREIGN KEY (salary_id) REFERENCES salaries (id),
ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES departments (id);