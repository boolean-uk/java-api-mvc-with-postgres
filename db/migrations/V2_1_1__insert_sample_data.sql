INSERT INTO department
        (department_name, department_location)
    VALUES
        ('Production', 'Sunndalsøra'),
        ('Quality Assurance', 'Årdal'),
        ('Maintenance', 'Sunndalsøra'),
        ('Logistics', 'Karmøy'),
        ('Research and Development', 'Oslo'),
        ('Human Resources', 'Oslo'),
        ('Finance', 'Oslo'),
        ('Safety', 'Oslo'),
        ('IT', 'Oslo');

UPDATE employee
    SET department_id = (SELECT id FROM department WHERE department_name = employee.department);