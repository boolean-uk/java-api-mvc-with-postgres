ALTER TABLE Employees
DROP COLUMN department;

ALTER TABLE Employees
ADD COLUMN department_id INTEGER NOT NULL;

ALTER TABLE Employees
ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES Departments(id);