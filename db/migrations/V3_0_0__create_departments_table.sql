CREATE TABLE departments(
    id serial primary key,
    name text,
    unique(name)
);