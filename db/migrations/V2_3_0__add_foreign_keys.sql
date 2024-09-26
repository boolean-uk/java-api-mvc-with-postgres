ALTER TABLE Employees
ADD COLUMN salary_id int,
ADD COLUMN department_id int;

ALTER TABLE Employees
ADD CONSTRAINT fk_salary FOREIGN KEY (salary_id) REFERENCES Salaries(id),
ADD CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES Departments(id);