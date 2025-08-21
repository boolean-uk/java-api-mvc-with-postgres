ALTER TABLE employees
ADD COLUMN department int;

ALTER TABLE employees
ADD CONSTRAINT fk_department FOREIGN KEY (department) REFERENCES departments(id) ON DELETE SET NULL;