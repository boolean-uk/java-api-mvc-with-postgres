-- Insert salary grades before referencing them as fks
ALTER TABLE employee
    ADD CONSTRAINT fk_salary_grade
    FOREIGN KEY (salary_grade)
    REFERENCES salary(grade);