package com.booleanuk.api.employee;

import jakarta.validation.constraints.NotBlank;

public class Employee {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String jobName;
    @NotBlank
    private String salaryGrade;
    @NotBlank
    private String department;

    public Employee(Long id,
                    String name,
                    String jobName,
                    String salaryGrade,
                    String department) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobName='" + jobName + '\'' +
                ", salaryGrade='" + salaryGrade + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getJobName() {
        return this.jobName;
    }

    public String getSalaryGrade() {
        return this.salaryGrade;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
