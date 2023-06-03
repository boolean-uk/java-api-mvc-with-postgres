INSERT INTO departments
    (name, location)
values
    ('d_name1', 'd_location1'),
    ('d_name2', 'd_location2'),
    ('d_name3', 'd_location3');


INSERT INTO salaries
    (grade, min_salary, max_salary)
values
    ("s_grade1", 1, 10),
    ("s_grade2", 2, 20);


INSERT INTO employees
    (name, job, salary_id, department_id)
values
    ('name1', 'job1', 1, 1),
    ('name2', 'job2', 2, 1),
    ('name3', 'job3', 2, 2),
    ('name4', 'job4', 2, 2),
    ('name5', 'job5', 1, 3);