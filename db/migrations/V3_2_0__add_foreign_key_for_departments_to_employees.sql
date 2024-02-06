ALTER TABLE employees
ADD COLUMN department_id INT NOT NULL;

ALTER TABLE employees
ADD CONSTRAINT fk_department FOREIGN KEY(department_id) REFERENCES departments(id);