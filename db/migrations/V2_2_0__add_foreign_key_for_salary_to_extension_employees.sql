ALTER TABLE extension_employees
ADD COLUMN salary_id INT,
ADD CONSTRAINT fk_salary_id
FOREIGN KEY (salary_id)
REFERENCES salaries(id);
