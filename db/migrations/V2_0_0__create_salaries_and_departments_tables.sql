CREATE TABLE IF NOT EXISTS Salaries (
grade text unique,
minSalary int not null,
maxSalary int not null
);

CREATE TABLE IF NOT EXISTS Departments (
name text unique,
location text not null
);