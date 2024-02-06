package com.booleanuk.api;

import com.booleanuk.api.requests.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

    @Test
    public void checkNewlyCreatedEmployee() {
        String name = "Sarah Bernard";
        String jobName = "Manager";
        String salaryGrade = "ABC";
        String department = "IT";

        Employee employee = new Employee(1, "Sarah Bernard", "Manager", "ABC", "IT");

        Assertions.assertEquals(employee.getName(), name);
        Assertions.assertEquals(employee.getJobName(), jobName);
        Assertions.assertEquals(employee.getSalaryGrade(), salaryGrade);
        Assertions.assertEquals(employee.getDepartment(), department);
    }
}
