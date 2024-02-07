ALTER TABLE Employees
ADD COLUMN department_id INT NOT NULL;

ALTER TABLE Employees
ADD CONSTRAINT fk_department_id
FOREIGN KEY (department_id)
REFERENCES Departments(id);
