

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
    (name, location)
VALUES
    ('Oslo1', 'Oslo'),
    ('Oslo2', 'Oslo'),
    ('Oslo3', 'Oslo'),
    ('Australia1', 'Australia'),
    ('Australia2', 'Australia'),
    ('Australia3', 'Australia'),
    ('Australia4', 'Australia'),
    ('Stockholm1', 'Stockholm'),
    ('Stockholm1', 'StockholmStockholm'),
    ('Stockholm1', 'Stockholm'),
    ('California1', 'California'),
    ('California2', 'California');

INSERT INTO Employee
    (name, jobName, grade, departmentId)
VALUES
    ('Bob','Builder','5',1),
    ('Greg','Dancer','10',2),
    ('Sara','President','1',1),
    ('Alf','Fairy','12',3),
    ('Toddy','DirtEater','2',5);

