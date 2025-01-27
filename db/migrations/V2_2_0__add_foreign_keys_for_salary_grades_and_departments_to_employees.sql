ALTER TABLE employees
ADD COLUMN salary_grade_id INTEGER;

ALTER TABLE employees
ADD CONSTRAINT fk_salary_id FOREIGN KEY(salary_grade_id) REFERENCES salary_grades(id);

ALTER TABLE employees
ADD COLUMN department_id INTEGER;

ALTER TABLE employees
ADD CONSTRAINT fk_department_id FOREIGN KEY(department_id) REFERENCES departments(id);