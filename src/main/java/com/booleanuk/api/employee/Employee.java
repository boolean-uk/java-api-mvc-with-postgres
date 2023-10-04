package com.booleanuk.api.employee;

public class Employee {

    private int id;
    private String fullname;
    private String jobName;
    private int salaryId;
    private int departmentId;

    public Employee() {}

    public Employee(int id, String fullname, String jobName, int salaryId, int departmentId) {
        this.id = id;
        this.fullname = fullname;
        this.jobName = jobName;
        this.salaryId = salaryId;
        this.departmentId = departmentId;
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

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", jobName='" + jobName + '\'' +
                ", salaryId=" + salaryId +
                ", departmentId=" + departmentId +
                '}';
    }
}
