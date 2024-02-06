ALTER TABLE employees

DROP COLUMN salaryGrade,
ADD COLUMN salaryGrade INT,
DROP COLUMN department,
ADD COLUMN department INT,

ADD CONSTRAINT fk_EmployeesSalary FOREIGN KEY (salaryGrade) REFERENCES salary(id),
ADD CONSTRAINT fk_EmployeeDepartment FOREIGN KEY (department) REFERENCES department(id);
