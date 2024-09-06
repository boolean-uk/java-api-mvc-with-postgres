ALTER TABLE Employees
ADD COLUMN department_id INT;

ALTER TABLE Employees
ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES Departments (id);