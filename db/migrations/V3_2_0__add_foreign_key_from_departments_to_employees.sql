ALTER TABLE IF EXISTS employees
ADD COLUMN departmentID INTEGER;

ALTER TABLE IF EXISTS employees
ADD CONSTRAINT fk_departmentID FOREIGN KEY(departmentID) REFERENCES departments(id);