UPDATE employee SET salary_id = stb.id
FROM salary stb
WHERE salaryGrade= stb.grade;

UPDATE employee SET department_id = dtb.id
FROM department dtb
WHERE department= dtb.name;
