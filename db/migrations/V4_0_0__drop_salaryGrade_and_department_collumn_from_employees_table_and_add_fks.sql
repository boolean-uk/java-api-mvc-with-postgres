ALTER TABLE Employees
DROP COLUMN salaryGrade,
DROP COLUMN department;

ALTER TABLE Employees
ADD COLUMN salaryGrade_id INT,
ADD COLUMN department_id INT;

ALTER TABLE Employees
ADD CONSTRAINT fk_salaryGrade_id FOREIGN KEY (salaryGrade_id) REFERENCES SalaryGrades (id),
ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES Departments (id);