CREATE TABLE IF NOT EXISTS Workers (
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    jobName VARCHAR(1024) NOT NULL,
    salaryGrade VARCHAR(255) NOT NULL,
    department VARCHAR(16)
)