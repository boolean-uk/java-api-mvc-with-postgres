ALTER TABLE Employees
ADD CONSTRAINT fk_salary_grade FOREIGN KEY (salaryGrade) REFERENCES Salaries (grade);

ALTER TABLE Employees
ADD CONSTRAINT fk_department FOREIGN KEY (department) REFERENCES Departments (name);



