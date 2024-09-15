CREATE TABLE Employee (
    employeeId SERIAL,
    name TEXT,
    jobName TEXT,
    salaryGrade TEXT,
    department TEXT,
    PRIMARY KEY(employeeId)
);


INSERT INTO Employee
    (name, jobName, salaryGrade, department)
VALUES
    ('Bob','Builder','5','Oslo'),
    ('Greg','Dancer','10','Dubai'),
    ('Sara','President','1','Washington'),
    ('Alf','Fairy','12','Earth'),
    ('Toddy','DirtEater','2','Earth');
