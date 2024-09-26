INSERT INTO Salaries
    (grade, minSalary, maxSalary)
VALUES
    ('6', 26000, 36000),
    ('7', 27000, 37000),
    ('8', 28000, 38000),
    ('9', 29000, 39000);

INSERT INTO Departments
    (name, location)
VALUES
    ('Software Development', 'East'),
    ('Artist Department', 'West'),
    ('Maths', 'North');

INSERT INTO Employees
    (name, jobName, salary_id, department_id)
VALUES
    ('Ada Lovelace', 'Java developer', (SELECT id FROM Salaries WHERE grade='9'), (SELECT id FROM Departments WHERE name='Software Development')),
    ('Bob', 'Python developer', (SELECT id FROM Salaries WHERE grade='7'), (SELECT id FROM Departments WHERE name='Software Development')),
    ('Vincent van Gogh', 'Painter',(SELECT id FROM Salaries WHERE grade='6'), (SELECT id FROM Departments WHERE name='Artist Department')),
    ('Freddie Mercury', 'Artist', (SELECT id FROM Salaries WHERE grade='9'), (SELECT id FROM Departments WHERE name='Artist Department'));