--UPDATE employees SET salary_id = 1, department_id = 1 where id=1;
--UPDATE employees SET salary_id = 2, department_id = 2 where id=2;
--UPDATE employees SET salary_id = 3, department_id = 3 where id=3;
--UPDATE employees SET salary_id = 4, department_id = 4 where id=4;
--UPDATE employees SET salary_id = 5, department_id = 5 where id=5;


INSERT INTO employees ( name, job_name, salary_id, department_id)
VALUES
   ('John Doe', 'Software Engineer', 1, 1),
   ('Jane Smith', 'Marketing Manager', 2, 2),
   ('Michael Johnson', 'Accountant', 3, 3),
   ('Emily Brown', 'HR Coordinator', 4, 4),
   ('David Lee', 'Sales Representative', 5, 5);
