ALTER TABLE departments
ADD CONSTRAINT unique_department_name UNIQUE (name);

ALTER TABLE employees
DROP CONSTRAINT fk_department;

ALTER TABLE employees
DROP COLUMN department_id;

ALTER TABLE employees
ADD COLUMN department_name TEXT;

ALTER TABLE employees
ADD CONSTRAINT fk_department
    FOREIGN KEY (department_name)
        REFERENCES departments(name);