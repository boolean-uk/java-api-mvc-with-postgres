ALTER TABLE employees
ADD COLUMN department INT;

ALTER TABLE employees
ADD CONSTRAINT fk_department_grade FOREIGN KEY (department) REFERENCES departments (id);