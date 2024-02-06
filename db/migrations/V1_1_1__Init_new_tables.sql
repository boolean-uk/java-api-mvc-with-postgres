CREATE TABLE Salary (
    grade TEXT,
    minSalary INT,
    maxSalary INT,
    PRIMARY KEY(salaryId)
);


CREATE TABLE Department (
    departmentId SERIAL,
    name TEXT,
    location TEXT,
    PRIMARY KEY(departmentId)
);


