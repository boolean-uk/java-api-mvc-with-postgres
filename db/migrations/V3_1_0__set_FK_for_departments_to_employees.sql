ALTER TABLE Employees
DROP COLUMN department,
ADD COLUMN department INT,
ADD CONSTRAINT fk_departments FOREIGN KEY (department) REFERENCES Departments(id);