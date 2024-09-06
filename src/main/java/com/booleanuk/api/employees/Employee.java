package com.booleanuk.api.employees;

public record Employee(
    int id,
    String name,
    String jobName,
    String salaryGrade,
    String department) {
}
