ALTER TABLE employee
ADD COLUMN salary_id INTEGER REFERENCES salary(id),
ADD COLUMN department_id INTEGER REFERENCES department(id);
