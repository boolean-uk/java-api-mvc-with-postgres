INSERT INTO salaries
    (salary_grade)
    VALUES
    ('3'),
    ('4'),
    ('5'),
    ('6'),
    ('7');

INSERT INTO departments
    (department)
    VALUES
    ('Engineering'),
    ('Analytics'),
    ('Project Management'),
    ('Marketing'),
    ('Finance'),
    ('Human Resources'),
    ('Operations'),
    ('Sales'),
    ('Customer Support'),
    ('Design');

INSERT INTO employees
    (name, job_name, salary_id, department_id)
VALUES
    ('John Smith', 'Software Engineer', (SELECT id FROM salaries WHERE salary_grade = '5'), (SELECT id FROM departments WHERE department = 'Engineering')),
    ('Jane Doe', 'Data Analyst', (SELECT id FROM salaries WHERE salary_grade = '4'), (SELECT id FROM departments WHERE department = 'Analytics')),
    ('Michael Johnson', 'Project Manager', (SELECT id FROM salaries WHERE salary_grade = '6'), (SELECT id FROM departments WHERE department = 'Project Management')),
    ('Emily Brown', 'Marketing Specialist', (SELECT id FROM salaries WHERE salary_grade = '4'), (SELECT id FROM departments WHERE department = 'Marketing')),
    ('Christopher Lee', 'Financial Analyst', (SELECT id FROM salaries WHERE salary_grade = '5'), (SELECT id FROM departments WHERE department = 'Finance')),
    ('Amanda Wilson', 'Human Resources Manager', (SELECT id FROM salaries WHERE salary_grade = '7'), (SELECT id FROM departments WHERE department = 'Human Resources')),
    ('David Taylor', 'Operations Manager', (SELECT id FROM salaries WHERE salary_grade = '7'), (SELECT id FROM departments WHERE department = 'Operations')),
    ('Sarah Martinez', 'Sales Representative', (SELECT id FROM salaries WHERE salary_grade = '3'), (SELECT id FROM departments WHERE department = 'Sales')),
    ('Daniel Anderson', 'Customer Support Specialist', (SELECT id FROM salaries WHERE salary_grade = '3'), (SELECT id FROM departments WHERE department = 'Customer Support')),
    ('Jessica Thompson', 'Graphic Designer', (SELECT id FROM salaries WHERE salary_grade = '4'), (SELECT id FROM departments WHERE department = 'Design')),
    ('Kevin Garcia', 'Software Developer', (SELECT id FROM salaries WHERE salary_grade = '5'), (SELECT id FROM departments WHERE department = 'Engineering')),
    ('Michelle Harris', 'Data Scientist', (SELECT id FROM salaries WHERE salary_grade = '6'), (SELECT id FROM departments WHERE department = 'Analytics')),
    ('Zoe Washington', 'Data Scientist', (SELECT id FROM salaries WHERE salary_grade = '7'), (SELECT id FROM departments WHERE department = 'Analytics')),
    ('Liam Richardson', 'Financial Planner', (SELECT id FROM salaries WHERE salary_grade = '6'), (SELECT id FROM departments WHERE department = 'Finance')),
    ('Hailey Hill', 'Marketing Analyst', (SELECT id FROM salaries WHERE salary_grade = '4'), (SELECT id FROM departments WHERE department = 'Marketing')),
    ('Jackson Gomez', 'Operations Analyst', (SELECT id FROM salaries WHERE salary_grade = '5'), (SELECT id FROM departments WHERE department = 'Operations')),
    ('Avery Carter', 'Sales Consultant', (SELECT id FROM salaries WHERE salary_grade = '4'), (SELECT id FROM departments WHERE department = 'Sales')),
    ('Mia Long', 'Technical Support Engineer', (SELECT id FROM salaries WHERE salary_grade = '4'), (SELECT id FROM departments WHERE department = 'Customer Support')),
    ('Elijah Reed', 'Graphic Designer', (SELECT id FROM salaries WHERE salary_grade = '5'), (SELECT id FROM departments WHERE department = 'Design')),
    ('Addison Torres', 'Software Engineer', (SELECT id FROM salaries WHERE salary_grade = '6'), (SELECT id FROM departments WHERE department = 'Engineering')),
    ('Evelyn Flores', 'Data Engineer', (SELECT id FROM salaries WHERE salary_grade = '7'), (SELECT id FROM departments WHERE department = 'Analytics')),
    ('Owen Morgan', 'Financial Advisor', (SELECT id FROM salaries WHERE salary_grade = '6'), (SELECT id FROM departments WHERE department = 'Finance')),
    ('Scarlett Coleman', 'Marketing Coordinator', (SELECT id FROM salaries WHERE salary_grade = '3'), (SELECT id FROM departments WHERE department = 'Marketing'));