CREATE TABLE IF NOT EXISTS departments (
    id serial primary key,
    name text not null,
    location text not null
)