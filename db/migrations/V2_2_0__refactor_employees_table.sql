ALTER TABLE Employees
ADD CONSTRAINT fk_salaryGrade FOREIGN KEY (salaryGrade) REFERENCES salaries(grade),
ADD CONSTRAINT fk_department FOREIGN KEY (department) REFERENCES departments(name);