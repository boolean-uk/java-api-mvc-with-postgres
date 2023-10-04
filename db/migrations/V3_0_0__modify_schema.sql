CREATE TABLE departments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    location VARCHAR(250)
);

CREATE TABLE salaries (
    id SERIAL PRIMARY KEY,
    grade VARCHAR(10),
    minSalary INTEGER,
    amount INTEGER
);

ALTER TABLE employees
    ADD COLUMN department_id INT,
    ADD COLUMN salary_id INT;

UPDATE employees
    SET department_id = departments.id
    FROM departments
    WHERE employees.department = departments.name;

UPDATE employees
    SET salary_id = salaries.id
    FROM salaries
    WHERE employees.salaryGrade = salaries.grade;

ALTER TABLE employees
    DROP COLUMN department,
    DROP COLUMN salaryGrade;

ALTER TABLE employees
    ADD CONSTRAINT fk_employee_department
    FOREIGN KEY (department_id) REFERENCES departments(id);

ALTER TABLE employees
    ADD CONSTRAINT fk_employee_salary
    FOREIGN KEY (salary_id) REFERENCES salaries(id);
