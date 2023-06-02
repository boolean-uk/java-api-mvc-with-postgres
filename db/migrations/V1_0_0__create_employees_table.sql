CREATE TABLE IF NOT EXISTS Employees (
id serial primary key,
name text not null,
jobName text,
salaryGrade text,
department text
)