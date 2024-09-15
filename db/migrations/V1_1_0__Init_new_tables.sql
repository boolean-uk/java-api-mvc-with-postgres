CREATE TABLE Salary (
    grade TEXT,
    minSalary INT,
    maxSalary INT,
    PRIMARY KEY(grade)
);


CREATE TABLE Department (
    departmentId SERIAL,
    name TEXT,
    location TEXT,
    PRIMARY KEY(departmentId)
);


