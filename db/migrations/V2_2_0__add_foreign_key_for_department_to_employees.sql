ALTER TABLE EMPLOYEES
ADD COLUMN department_id,
ADD CONSTRAINT fk_department_id
FOREIGN KEY (department_id)
REFERENCES departments(id);