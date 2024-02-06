INSERT INTO departments
    (name, location)
VALUES
    ('Software Development', 'London'),
    ('Leader group', 'London'),
    ('HR', 'Trondheim');

UPDATE employees
SET department_id = 1
WHERE id = 1;

UPDATE employees
SET department_id = 2
WHERE id = 2;

UPDATE employees
SET department_id = 3
WHERE id = 3;