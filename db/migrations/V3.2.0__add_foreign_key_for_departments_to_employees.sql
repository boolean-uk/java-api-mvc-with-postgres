ALTER TABLE Employees
ADD COLUMN department_pseudo VARCHAR(50);

UPDATE Employees
SET department_pseudo = Employees.department;

ALTER TABLE Employees
DROP COLUMN department;

ALTER TABLE Employees
ADD COLUMN department INT;

ALTER TABLE Employees
ADD CONSTRAINT fk_department FOREIGN KEY (department) REFERENCES Departments(id);

UPDATE Employees
SET department = (SELECT id from Departments WHERE Departments.name = 'Transport')
WHERE department_pseudo = 'Transport';

UPDATE Employees
SET department = (SELECT id from Departments WHERE Departments.name = 'HR')
WHERE department_pseudo = 'HR';

UPDATE Employees
SET department = (SELECT id from Departments WHERE Departments.name = 'Marketing')
WHERE department_pseudo = 'Marketing';

UPDATE Employees
SET department = (SELECT id from Departments WHERE Departments.name = 'Software Development')
WHERE department_pseudo = 'Software Development';

UPDATE Employees
    SET department = (SELECT id from Departments WHERE Departments.name = 'Analytics')
WHERE department_pseudo = 'Analytics';

ALTER TABLE Employees
DROP COLUMN department_pseudo;