ALTER TABLE Employees
DROP COLUMN salary_grade,
ADD COLUMN salary_grade INT,
ADD CONSTRAINT fk_salary_grades FOREIGN KEY (salary_grade) REFERENCES Salary_grades(id);