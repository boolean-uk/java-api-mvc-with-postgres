ALTER TABLE Employees
DROP COLUMN salaryGrade;
ALTER TABLE employees
ADD COLUMN salaryGrade_id INT;
ALTER TABLE employees


ADD CONSTRAINT fk_salary_id FOREIGN KEY (salaryGrade_id) REFERENCES Salaries (id);

ALTER TABLE employees
ADD COLUMN department_id INT;