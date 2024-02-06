INSERT INTO Departments (name) VALUES
    ('Human Resources'),
    ('Finance'),
    ('Marketing'),
    ('Information Technology');

INSERT INTO Salaries (name) VALUES
    ('Grade A'),
    ('Grade B'),
    ('Grade C');

INSERT INTO Employees (name, jobName, salary_id, department_id) VALUES
    ('John Doe', 'HR Specialist', 1, 1),
    ('Jane Smith', 'Financial Analyst', 2, 2),
    ('Bob Johnson', 'Marketing Coordinator', 3, 3),
    ('Alice Brown', 'IT Specialist', 1, 4),
    ('Charlie Davis', 'HR Manager', 3, 1),
    ('Eva White', 'Software Developer', 2, 4);
