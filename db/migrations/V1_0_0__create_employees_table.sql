CREATE TABLE Employee(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(250),
    job_name VARCHAR(250),
    salary_grade VARCHAR(10),
    department VARCHAR(50)
);
