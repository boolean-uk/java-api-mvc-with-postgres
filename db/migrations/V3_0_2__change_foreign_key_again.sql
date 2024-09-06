ALTER TABLE employees
DROP CONSTRAINT fk_department;

ALTER TABLE employees
DROP COLUMN department_name;

ALTER TABLE employees
ADD COLUMN department TEXT;

ALTER TABLE employees
ADD CONSTRAINT fk_department
    FOREIGN KEY (department)
        REFERENCES departments(name);