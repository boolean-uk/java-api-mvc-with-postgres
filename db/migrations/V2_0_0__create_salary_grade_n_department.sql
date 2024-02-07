CREATE TABLE salary_grades(
    grade text PRIMARY KEY,
    min_salary int,
    max_salary int
)

CREATE TABLE departments(
    id serial PRIMARY KEY,
    name text,
    location text
)