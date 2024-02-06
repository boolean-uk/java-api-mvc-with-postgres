INSERT INTO department
        (department_name)
    VALUES
        ('Production'),
        ('Quality Assurance'),
        ('Maintenance'),
        ('Logistics'),
        ('Research and Development'),
        ('Human Resources'),
        ('Finance'),
        ('Safety'),
        ('IT');

UPDATE employee
    SET department_id = (SELECT id FROM department WHERE department_name = employee.department);