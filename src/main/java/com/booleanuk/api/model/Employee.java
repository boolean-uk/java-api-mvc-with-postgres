package com.booleanuk.api.model;

public class Employee {
int id;
String name;
String jobName;
String salaryGrade;
String department;

public Employee(int id, String name, String jobName, String salaryGrade, String department){
    this.id = id;
    this.name = name;
    this.jobName = jobName;
    this.salaryGrade = salaryGrade;
    this.department = department;
}

}
