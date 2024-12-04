CREATE TABLE IF NOT EXISTS salaries (
    id serial primary key,
    grade text not null,
    min_salary int not null,
    max_salary int not null
)