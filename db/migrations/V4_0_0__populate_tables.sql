INSERT INTO salaries
    (grade, minSalary, maxSalary)
VALUES
    ('1', 3000, 10000),
    ('2', 10000, 13000),
    ('3', 13000, 18000),
    ('4', 18000, 23000),
    ('5', 23000, 27000),
    ('6', 27000, 32000),
    ('7', 32000, 37000),
    ('8', 37000, 42000),
    ('9', 42000, 47000),
    ('10', 47000, 60000);


INSERT INTO departments
    (name, location)
VALUES
    ('Software development', 'Gothenburg, Sweden'),
    ('Cyber security', 'Stockholm, Sweden'),
    ('Cloud computing', 'Malmo, Sweden'),
    ('Test engineering', 'Torslanda, Sweden');




INSERT INTO employees
    (name, jobName, salaryID, departmentID)
VALUES
    ('Mattias', 'Full-stack developer', 5, 1),
    ('Julia', 'Engineer', 6, 2),
    ('Johanna', 'Student', 1, 1);