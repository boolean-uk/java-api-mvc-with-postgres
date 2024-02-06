package com.booleanuk.api.model;


import com.sun.jdi.InvalidTypeException;

import java.util.InputMismatchException;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private int departmentId;

    public Employee(int id, String name, String jobName, String salaryGrade, int departmentId) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.departmentId = departmentId;
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

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public boolean valuesAreAllowed(Employee employee) {
        if(Integer.parseInt(employee.getSalaryGrade()) > 100 || Integer.parseInt(employee.getSalaryGrade()) < 0) {
            return false;
        }
        return true;
    }
}
