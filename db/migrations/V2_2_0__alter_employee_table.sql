ALTER TABLE Employee
DROP COLUMN Salary_Grade,
DROP COLUMN Department;

DELETE FROM Employee;
ALTER SEQUENCE Employee_ID_seq RESTART WITH 1; --reset ID to 1

ALTER TABLE Employee
ADD COLUMN Salary_ID INT NOT NULL,
ADD COLUMN Department_ID INT NOT NULL;

ALTER TABLE Employee
ADD CONSTRAINT fk_salary_id FOREIGN KEY (Salary_ID) REFERENCES Salary (id),
ADD CONSTRAINT fk_department_id FOREIGN KEY (Department_ID) REFERENCES Department (id);