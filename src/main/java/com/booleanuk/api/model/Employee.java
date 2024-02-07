package com.booleanuk.api.model;


import com.sun.jdi.InvalidTypeException;

import java.util.InputMismatchException;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private String grade;
    private int departmentId;

    public Employee(int id, String name, String jobName, String grade, int departmentId) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.grade = grade;
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
        return grade;
    }

    public void setSalaryGrade(String grade) throws InputMismatchException {
        if(Integer.parseInt(grade) > 100 || Integer.parseInt(grade) < 0) {
            throw new InputMismatchException("Grade higher than 100 or less than 0");
        }
        this.grade = grade;
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
