package com.booleanuk.api.employee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    public void testEmployeeConstructor() {
        Employee employee = new Employee(1, "Ex Ample", "Tester", "EXEMPLARY", "Testing");
        Assertions.assertEquals("Ex Ample", employee.getName());
        Assertions.assertEquals("Tester", employee.getJobName());
        Assertions.assertEquals("EXEMPLARY", employee.getSalaryGrade());
        Assertions.assertEquals("Testing", employee.getDepartment());
    }
}