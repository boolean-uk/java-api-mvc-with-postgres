ALTER TABLE employee
ADD COLUMN department_id INTEGER REFERENCES departments(id);