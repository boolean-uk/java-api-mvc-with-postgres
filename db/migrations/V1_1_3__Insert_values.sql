

INSERT INTO Salary
    (grade, minSalary, maxSalary)
VALUES
    ('1', 1, 1000),
    ('2', 1000, 1499),
    ('3', 1500, 1999),
    ('4', 2000, 2499),
    ('5', 3000, 3999),
    ('6', 4000, 4999),
    ('7', 5000, 599),
    ('8', 6000, 6999),
    ('9', 7000, 7999),
    ('10', 8000, 8999),
    ('11', 9000, 9999),
    ('12', 10000, 19999);


INSERT INTO Department
    (departmentId, name, location)
VALUES
    (1, 'Oslo1', 'Oslo'),
    (2, 'Oslo2', 'Oslo'),
    (3, 'Oslo3', 'Oslo'),
    (4, 'Australia1', 'Australia'),
    (5, 'Australia2', 'Australia'),
    (6, 'Australia3', 'Australia'),
    (7, 'Australia4', 'Australia'),
    (8, 'Stockholm1', 'Stockholm'),
    (9, 'Stockholm1', 'StockholmStockholm'),
    (10, 'Stockholm1', 'Stockholm'),
    (11, 'California1', 'California'),
    (12, 'California2', 'California');

INSERT INTO Employee
    (name, jobName, grade, departmentId)
VALUES
    ('Bob','Builder','5',1),
    ('Greg','Dancer','10',2),
    ('Sara','President','1',1),
    ('Alf','Fairy','12',3),
    ('Toddy','DirtEater','2',5);

