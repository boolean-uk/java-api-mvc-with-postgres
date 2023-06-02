CREATE TABLE IF NOT EXISTS Department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    location VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS Salary (
    id SERIAL PRIMARY KEY,
    grade VARCHAR(10),
    minSalary INTEGER,
    maxSalary INTEGER
);



ALTER TABLE Employee
ADD COLUMN salary_id INTEGER,
ADD CONSTRAINT fk_salary FOREIGN KEY (salary_id) REFERENCES Salary(id),
ADD COLUMN department_id INTEGER,
ADD CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES Department(id);



--CREATE TABLE IF NOT EXISTS Employee (
--    id SERIAL PRIMARY KEY,
--    name VARCHAR(255),
--    jobName VARCHAR(255),
--    salaryGrade VARCHAR(10),
--    department VARCHAR(50)
--);
