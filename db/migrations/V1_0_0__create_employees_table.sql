CREATE TABLE IF NOT EXISTS Employees (
id serial primary key,
name text not null,
jobName text not null,
salaryGrade text not null,
department text not null
)