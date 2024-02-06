package com.booleanuk.api.employee;

public class Employee {
    private int id;
    private String name;
    private String job_name;
    private String salary_grade;
    private String department;

    public Employee(int id, String name, String job_name, String salary_grade, String department) {
        this.id = id;
        this.name = name;
        this.job_name = job_name;
        this.salary_grade = salary_grade;
        this.department = department;
    }

    public String toString() {
        String result = this.id + " - ";
        result += this.name + " - ";
        result += this.job_name + " - ";
        result += this.salary_grade + " - ";
        result += this.department;
        return result;
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

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getSalary_grade() {
        return salary_grade;
    }

    public void setSalary_grade(String salary_grade) {
        this.salary_grade = salary_grade;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
