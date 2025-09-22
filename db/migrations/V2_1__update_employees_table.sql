ALTER TABLE Employees
    DROP COLUMN salaryGrade;

ALTER TABLE Employees
    ADD COLUMN salary_id INT;

ALTER TABLE Employees
    ADD CONSTRAINT fk_salary_id FOREIGN KEY (salary_id) REFERENCES SalaryGrades (id) ON DELETE SET NULL;