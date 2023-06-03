ALTER TABLE extension_employees
ADD COLUMN department_id INT,
ADD CONSTRAINT fk_department_id
FOREIGN KEY (department_id)
REFERENCES departments(id);
