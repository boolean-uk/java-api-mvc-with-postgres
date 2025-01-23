ALTER TABLE employees
ADD COLUMN salaryGrade INT;

ALTER TABLE employees
ADD CONSTRAINT fk_salary_grade FOREIGN KEY (salaryGrade) REFERENCES salaries (id);