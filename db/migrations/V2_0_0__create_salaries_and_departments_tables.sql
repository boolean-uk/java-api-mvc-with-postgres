CREATE TABLE IF NOT EXISTS Salaries (
grade text NOT NULL PRIMARY KEY,
minSalary int not null,
maxSalary int not null
);

CREATE TABLE IF NOT EXISTS Departments (
name text NOT NULL PRIMARY KEY,
location text not null
);