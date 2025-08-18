DELETE FROM Employees;

ALTER TABLE Employees
ADD COLUMN salaryGrade_id serial,
ADD COLUMN department_id serial;

ALTER TABLE Employees
ADD CONSTRAINT fk_salaryGrade_id FOREIGN KEY (salaryGrade_id) REFERENCES SalaryGrades (id),
ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES Departments (id);