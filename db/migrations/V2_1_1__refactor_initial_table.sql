ALTER TABLE Employees
ADD COLUMN salary_id INT REFERENCES Salaries(id);
ALTER TABLE Employees
ADD COLUMN department_id INT REFERENCES Departments(id);

UPDATE Employees
SET salary_id = (
    SELECT id
    FROM Salaries
    WHERE Employees.salary_grade = Salaries.grade
);
UPDATE Employees
SET department_id = (
    SELECT id
    FROM Departments
    WHERE Employees.department = Departments.name
);
ALTER TABLE Employees
DROP COLUMN salary_grade,
DROP COLUMN department;
