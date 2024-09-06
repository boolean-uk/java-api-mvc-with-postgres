ALTER TABLE employees
DROP COLUMN salaryGrade,
DROP COLUMN department,

ADD COLUMN salary_id int,
ADD COLUMN department_id int,

ADD CONSTRAINT fk_EmployeeSalary FOREIGN KEY (salary_id) REFERENCES salaries(id),
ADD CONSTRAINT fk_EmployeeDepartment FOREIGN KEY (department_id) REFERENCES departments(id);