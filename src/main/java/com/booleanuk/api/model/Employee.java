package com.booleanuk.api.model;


import com.sun.jdi.InvalidTypeException;

import java.util.InputMismatchException;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Employee(int id, String name, String jobName, String salaryGrade, String department) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getSalaryGrade() {
        return salaryGrade;
    }

    public void setSalaryGrade(String salaryGrade) throws InputMismatchException {
        if(Integer.parseInt(salaryGrade) > 100 || Integer.parseInt(salaryGrade) < 0) {
            throw new InputMismatchException("SalaryGrade higher than 100 or less than 0");
        }
        this.salaryGrade = salaryGrade;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
