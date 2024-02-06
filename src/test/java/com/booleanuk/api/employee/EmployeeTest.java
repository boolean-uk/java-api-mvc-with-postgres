package com.booleanuk.api.employee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    public void testadd() throws SQLException {
        Employee employee = new Employee(1, "Axel", "Programmer", 4, "IT");
        EmployeeRepository employeeRepository = new EmployeeRepository();

        Assertions.assertEquals(1, employeeRepository.add(employee).getId());
        Assertions.assertEquals("Axel", employeeRepository.add(employee).getName());
        Assertions.assertEquals("Programmer", employeeRepository.add(employee).getJobName());
        Assertions.assertEquals(4, employeeRepository.add(employee).getSalaryGrade());
        Assertions.assertEquals("IT", employeeRepository.add(employee).getDepartment());

    }
}