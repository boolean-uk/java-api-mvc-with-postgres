package com.booleanuk.api.employee;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
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
}
