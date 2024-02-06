CREATE TABLE IF NOT EXISTS Employees(
	id serial primary key,
	name varchar(250) not null,
	jobName varchar(250),
	salaryGrade varchar(10),
	department varchar(50)
);