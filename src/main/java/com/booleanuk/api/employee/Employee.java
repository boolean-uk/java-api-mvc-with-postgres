package com.booleanuk.api.employee;

public class Employee {

    private int id;
    private String fullname;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Employee() {}

    public Employee(int id, String fullname, String jobName, String salaryGrade, String department) {
        this.id = id;
        this.fullname = fullname;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public void setSalaryGrade(String salaryGrade) {
        this.salaryGrade = salaryGrade;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", jobName='" + jobName + '\'' +
                ", salaryGrade='" + salaryGrade + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
