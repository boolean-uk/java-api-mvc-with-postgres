ALTER TABLE employees
    ADD CONSTRAINT fk_salary_grade
  	    FOREIGN KEY(salary_id)
  		    REFERENCES salaries(id);

ALTER TABLE employees
    ADD CONSTRAINT fk_department
  	    FOREIGN KEY(department_id)
  		    REFERENCES departments(id);
