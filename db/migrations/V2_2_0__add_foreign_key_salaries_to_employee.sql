ALTER TABLE employee
    ADD COLUMN salaries_id INTEGER REFERENCES salaries(id);