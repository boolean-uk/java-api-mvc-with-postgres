INSERT INTO departments
(name)
VALUES
('Sales'),
('Marketing');

INSERT INTO salaries
(grade, minSalary, maxSalary)
VALUES
('A', 100000, 150000),
('B', 75000, 100000),
('C', 50000, 75000);

INSERT INTO employees
(name, jobName, salary_id, department_id)
VALUES
('John Doe', 'Sales Manager', 1, 1),
('Jane Doe', 'Sales Associate', 2, 1),
('Jim Doe', 'Marketing Manager', 1, 2),
('Jill Doe', 'Marketing Associate', 2, 2);