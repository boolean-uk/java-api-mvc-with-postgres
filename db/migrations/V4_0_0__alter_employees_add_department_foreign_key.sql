ALTER TABLE employees
ADD COLUMN department_id INTEGER;

ALTER TABLE employees
ADD CONSTRAINT fk_department_id
    FOREIGN KEY (department_id)
        REFERENCES departments(id);