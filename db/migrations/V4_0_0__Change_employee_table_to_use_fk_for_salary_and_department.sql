ALTER TABLE employees
ADD COLUMN salary_grade_id INT;

UPDATE employees
SET salary_grade_id = (
    SELECT id
        FROM salaries
            WHERE salaries.grade = employees.salary_grade
);

ALTER TABLE employees
ADD CONSTRAINT fk_salary_grade_id FOREIGN KEY (salary_grade_id) REFERENCES salaries(id);

ALTER TABLE employees
DROP COLUMN salary_grade;

ALTER TABLE employees
ADD COLUMN department_id INT;

UPDATE employees
SET department_id = (
SELECT id
    FROM departments
        WHERE departments.name = employees.department
);

ALTER TABLE employees
ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES departments(id);

ALTER TABLE employees
DROP COLUMN department;