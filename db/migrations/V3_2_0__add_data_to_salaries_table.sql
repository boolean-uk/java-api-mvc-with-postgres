INSERT INTO salaries
    (grade, minSalary, maxSalary)
VALUES
    ('6', 15000, 20000),
    ('8', 25000, 30000),
    ('10', 35000, 40000);

UPDATE employees
SET salary_id = 2
WHERE id = 1;

UPDATE employees
SET salary_id = 3
WHERE id = 2;

UPDATE employees
SET salary_id = 1
WHERE id = 3;