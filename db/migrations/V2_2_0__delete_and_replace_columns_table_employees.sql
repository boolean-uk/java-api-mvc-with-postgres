ALTER TABLE employees
DROP COLUMN salaryGrade,
DROP COLUMN department;

ALTER TABLE employees
ADD COLUMN salaryGrade INTEGER,
ADD COLUMN department INTEGER;

ALTER TABLE employees
ADD CONSTRAINT fk_salaryGrade FOREIGN KEY (salaryGrade) REFERENCES salaries,
ADD CONSTRAINT fk_department FOREIGN KEY (department) REFERENCES departments;