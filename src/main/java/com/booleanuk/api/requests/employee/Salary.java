package com.booleanuk.api.requests.employee;

public class Salary {
    private long id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Salary(long id, String name, String jobName, String salaryGrade, String department) {
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    public void setId(long id) {
        this.id = id;
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
}
