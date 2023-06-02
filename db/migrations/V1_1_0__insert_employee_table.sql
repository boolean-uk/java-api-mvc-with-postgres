DROP TABLE employee;
CREATE TABLE If NOT EXISTS employee
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    jobName TEXT,
    salaryGrade TEXT,
    department TEXT
);

INSERT INTO employee (name,jobName, salaryGrade, department)
VALUES ('Rose Breedveld','Developer','A','Java'),
 ('Farhang Younis','Teacher','B','C#'),
 ('Whoever','Engineer','D','C++'),
 ('Rita Nielsen','Designer','F','Python'),
 ('July John','Architect','G','C');