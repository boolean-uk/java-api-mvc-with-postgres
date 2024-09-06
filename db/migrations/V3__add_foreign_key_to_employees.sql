ALTER TABLE employees
  ADD CONSTRAINT fk_salary_grade
  FOREIGN KEY (salary_grade) REFERENCES salaries(grade)
