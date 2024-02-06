DROP TABLE Employees;


CREATE TABLE Departments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250)
);

CREATE TABLE Salaries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250)
);

CREATE TABLE Employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250),
    jobName VARCHAR(250),
    salary_id INT,
    department_id INT,
    CONSTRAINT fk_salary_id
        FOREIGN KEY(salary_id)
          REFERENCES Salaries(id),
    CONSTRAINT fk_department_id
        FOREIGN KEY(department_id)
          REFERENCES Departments(id)
);