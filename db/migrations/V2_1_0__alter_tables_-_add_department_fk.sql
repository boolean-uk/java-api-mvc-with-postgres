ALTER TABLE employee
    ADD COLUMN department_id INT REFERENCES department(id);