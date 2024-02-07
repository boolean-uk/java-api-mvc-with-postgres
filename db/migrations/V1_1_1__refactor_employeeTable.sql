TRUNCATE Employee;

ALTER TABLE Employee
DROP COLUMN salaryGrade,
ADD COLUMN grade TEXT,
ADD CONSTRAINT grade FOREIGN KEY (grade) REFERENCES Salary (grade);


ALTER TABLE Employee
DROP COLUMN department,
ADD COLUMN departmentId INT,
ADD CONSTRAINT departmentId FOREIGN KEY (departmentId) REFERENCES Department (departmentId);