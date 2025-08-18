
DELETE FROM Employees;

ALTER TABLE Employees
DROP COLUMN salarygrade,
ADD COLUMN salaryGrade_id INT,
ADD CONSTRAINT fk_salaryGrade_id FOREIGN KEY (salaryGrade_id) REFERENCES salaryGrades (id);

ALTER TABLE Employees
DROP COLUMN department,
ADD COLUMN department_id INT,
ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES departments (id);


INSERT INTO Employees (name, jobName, salaryGrade_id, department_id)
VALUES
('Ada Lovelace', 'CEO', 4, 2),
('Giovani', 'Hacker',3, 1),
('Dave', 'Software Developer', 2, 1)
