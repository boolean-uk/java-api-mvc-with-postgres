-- Populate Salary Table
INSERT INTO Salary (grade, min_salary, max_salary)
VALUES
    ('1', 1000, 2000),
    ('2', 2000, 3000),
    ('3', 3000, 4000),
    ('4', 4000, 5000),
    ('5', 5000, 6000),
    ('6', 6000, 7000),
    ('7', 7000, 8000),
    ('8', 8000, 9000),
    ('9', 9000, 10000),
    ('10', 10000, 11000);

-- Populate Department Table
INSERT INTO Department (name, location)
VALUES
    ('Sales', 'New York'),
    ('Marketing', 'London'),
    ('Finance', 'Paris'),
    ('Human Resources', 'Berlin'),
    ('IT', 'Denmark'),
    ('Research and Development', 'Tokyo');

-- Populate Employee Table
INSERT INTO  Employee
    (Name, Job_Name, Salary_ID, Department_ID)
VALUES
    ('John Doe', 'Software Developer', 8, 5),
    ('Jane Smith', 'Sales Manager', 6, 1),
    ('Michael Johnson', 'HR Specialist', 6, 4),
    ('Emily Davis', 'Marketing Coordinator', 5, 2),
    ('David Brown', 'Finance Analyst', 4, 3),
    ('Gregory Daniels', 'Research and Development Specialist', 9, 6);