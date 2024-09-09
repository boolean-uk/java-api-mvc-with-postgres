ALTER TABLE employees
ADD COLUMN salary_id INTEGER;

ALTER TABLE employees
ADD CONSTRAINT fk_salary_id
    FOREIGN KEY (salary_id)
        REFERENCES salaries(id);