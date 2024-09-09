ALTER TABLE Employees
ADD COLUMN salary_grade VARCHAR(3);

ALTER TABLE Employees
ADD CONSTRAINT fk_salary_grade FOREIGN KEY (salary_grade) REFERENCES Salaries(grade);

UPDATE Employees
SET salary_grade = (SELECT grade from Salaries WHERE Salaries.grade = Employees.salaryGrade);