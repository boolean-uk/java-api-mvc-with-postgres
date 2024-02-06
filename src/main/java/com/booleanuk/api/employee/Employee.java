package com.booleanuk.api.employee;

import com.booleanuk.api.department.Department;
import com.booleanuk.api.salary.Salary;
import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String jobName;

    private String salaryGrade;

    private String department;


    private Employee(){

    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJobName() {
        return jobName;
    }

    public String getSalaryGrade() {
        return salaryGrade;
    }

    public String getDepartment() {
        return department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setSalaryGrade(String salaryGrade) {
        this.salaryGrade = salaryGrade;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
