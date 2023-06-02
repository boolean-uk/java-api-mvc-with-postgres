create table employees (
   id serial primary key,
   name text not null,
   jobName text not null,
   salary_id integer not null,
   department_id integer not null,
    FOREIGN KEY (salary_id) REFERENCES salaries(id),
    FOREIGN KEY (department_id) REFERENCES departments(id)
);