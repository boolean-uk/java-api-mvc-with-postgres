ALTER TABLE employees
    ADD COLUMN salaryGrade_id INT;

ALTER TABLE employees
    ADD CONSTRAINT fk_salaryGrade_id
        FOREIGN KEY(salaryGrade_id)
            REFERENCES salaries(id);