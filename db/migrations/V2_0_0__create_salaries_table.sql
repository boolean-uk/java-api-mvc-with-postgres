CREATE TABLE salaries(
    id serial primary key,
    grade text,
    minSalary integer,
    maxSalary integer,
    unique(grade)
);