package com.booleanuk.api;

import com.booleanuk.api.requests.employee.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

    @Test
    public void checkNewlyCreatedEmployee() {
        String name = "Sarah Bernard";
        String jobName = "Manager";
        String salaryGrade = "ABC";
        String department = "IT";

        Employee salary = new Employee(1, "Sarah Bernard", "Manager", "ABC", "IT");

        Assertions.assertEquals(salary.getName(), name);
        Assertions.assertEquals(salary.getJobName(), jobName);
        Assertions.assertEquals(salary.getSalaryGrade(), salaryGrade);
        Assertions.assertEquals(salary.getDepartment(), department);
    }
}
