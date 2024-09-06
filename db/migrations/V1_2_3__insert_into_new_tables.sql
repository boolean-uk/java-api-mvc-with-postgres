TRUNCATE TABLE employees;

INSERT INTO salaries (grade, minSalary, maxSalary)
VALUES
    ('A', 50000, 70000),
    ('B', 70001, 90000),
    ('C', 90001, 110000),
    ('D', 110001, 130000),
    ('E', 130001, 150000);

INSERT INTO departments (name, location)
VALUES
    ('HR', 'New York'),
    ('Finance', 'London'),
    ('Engineering', 'San Francisco'),
    ('Marketing', 'Berlin'),
    ('Operations', 'Tokyo');


INSERT INTO employees (name, jobName, salary_id, department_id)
VALUES
    ('John Doe', 'HR Manager', 1, 1),
    ('Jane Smith', 'Financial Analyst', 2, 2),
    ('David Johnson', 'Software Engineer', 3, 3),
    ('Emily Brown', 'Marketing Specialist', 2, 4),
    ('Michael Wilson', 'Operations Manager', 4, 5),
    ('Sarah Miller', 'Data Scientist', 3, 3),
    ('James Davis', 'HR Coordinator', 1, 1),
    ('Laura Taylor', 'Finance Director', 4, 2),
    ('Paul Anderson', 'Marketing Director', 4, 4),
    ('Linda Harris', 'Engineering Manager', 5, 3);

