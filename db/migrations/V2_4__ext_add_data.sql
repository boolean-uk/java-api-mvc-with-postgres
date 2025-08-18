INSERT INTO SalaryGrades (grade, minSalary, maxSalary) VALUES
('Grade 1', 30000, 45000),
('Grade 2', 45001, 60000),
('Grade 3', 60001, 80000),
('Grade 4', 80001, 100000);

INSERT INTO Departments (name, location) VALUES
('IT', 'Head Office'),
('Operations', 'Branch A'),
('Finance', 'Head Office'),
('Human Resources', 'Branch B'),
('Design', 'Head Office'),
('Marketing', 'Branch C'),
('Strategy', 'Head Office');

INSERT INTO Employees (name, jobname, salaryGrade_id, department_id) VALUES
('Alice Johnson', 'Software Engineer', 3, 1),
('Bob Smith', 'Project Manager', 4, 2),
('Charlie Lee', 'Data Analyst', 2, 3),
('Diana Martinez', 'HR Specialist', 2, 4),
('Ethan Brown', 'UX Designer', 3, 5),
('Fiona Davis', 'Marketing Coordinator', 1, 6),
('George Wilson', 'System Administrator', 3, 1),
('Hannah White', 'Business Analyst', 4, 7);
