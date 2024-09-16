ALTER TABLE employees
DROP COLUMN salary_grade,
ADD COLUMN salary_id INT;

ALTER TABLE employees
DROP COLUMN department,
ADD COLUMN department_id INT;