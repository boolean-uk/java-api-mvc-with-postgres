ALTER TABLE Employees
ADD COLUMN salary_id INT NOT NULL;

ALTER TABLE Employees
ADD CONSTRAINT fk_salary_id
FOREIGN KEY (salary_id)
REFERENCES Salaries(id);
