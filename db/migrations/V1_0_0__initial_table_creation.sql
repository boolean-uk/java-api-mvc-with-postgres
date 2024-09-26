CREATE TABLE IF NOT EXISTS Employees(
    id serial primary key,
    fullname varchar(255) not null,
    jobName varchar(255),
    salaryGrade varchar(10),
    department varchar(50)
    );