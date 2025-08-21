
ALTER TABLE employees
ADD COLUMN salaryGrade int;

ALTER TABLE employees
ADD CONSTRAINT fk_salary_grade FOREIGN KEY (salaryGrade) REFERENCES salaries(id) ON DELETE SET NULL;