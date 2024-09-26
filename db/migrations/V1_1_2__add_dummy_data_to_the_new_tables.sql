INSERT INTO Department (name, location) VALUES
('Marketing', 'New York'),
('Sales', 'Los Angeles'),
('Finance', 'Chicago'),
('Human Resources', 'Houston');

INSERT INTO Salary (grade, minSalary, maxSalary) VALUES
('A', 50000, 70000),
('B', 60000, 80000),
('C', 70000, 90000),
('D', 80000, 100000);

INSERT INTO Employee (name, jobName, salary_id, department_id) VALUES
('John Doe', 'Marketing Specialist', 1, 1),
('Jane Smith', 'Sales Manager', 2, 2),
('Michael Johnson', 'Financial Analyst', 3, 3),
('Sarah Williams', 'HR Coordinator', 4, 4);