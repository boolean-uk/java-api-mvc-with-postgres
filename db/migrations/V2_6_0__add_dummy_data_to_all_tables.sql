INSERT INTO salaries
    (salary_grade, min_salary, max_salary)
    VALUES
    ('3', 15000, 24999),
    ('4', 25000, 34999),
    ('5', 35000, 44999),
    ('6', 45000, 54999),
    ('7', 55000, 100000);

INSERT INTO departments
    (department, location)
    VALUES
    ('Engineering', 'Copenhagen'),
    ('Analytics', 'Copenhagen'),
    ('Project Management', 'Copenhagen'),
    ('Marketing', 'Copenhagen'),
    ('Finance', 'Copenhagen'),
    ('Human Resources', 'Copenhagen'),
    ('Operations', 'Copenhagen'),
    ('Sales', 'Copenhagen'),
    ('Customer Support', 'Copenhagen'),
    ('Design', 'Copenhagen');

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