CREATE TABLE IF NOT EXISTS employees (
    id serial primary key,
    name text not null,
    job_name text not null,
    salary_grade text not null,
    department text not null
)