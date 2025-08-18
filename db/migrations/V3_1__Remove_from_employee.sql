ALTER TABLE Employees
DROP COLUMN department;



ALTER TABLE employees
ADD CONSTRAINT fk_dep_id FOREIGN KEY (department_id) REFERENCES departments (id);


