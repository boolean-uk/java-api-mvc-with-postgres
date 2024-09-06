ALTER TABLE employees
ADD COLUMN salaryGrade TEXT,
ADD COLUMN department_id INTEGER;

ALTER TABLE employees
ADD CONSTRAINT fk_salaryGrade
    FOREIGN KEY (salaryGrade)
        REFERENCES salaries(grade),
ADD CONSTRAINT fk_department
    FOREIGN KEY (department_id)
        REFERENCES departments(id);