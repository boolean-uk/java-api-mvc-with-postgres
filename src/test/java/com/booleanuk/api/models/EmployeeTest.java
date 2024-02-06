package com.booleanuk.api.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    public void testEmployeeConstructor() {
        Employee employee = new Employee(1, "Ex Ample", "Tester", "EXEMPLARY", "Testing");
        Assertions.assertEquals(1, employee.getId());
        Assertions.assertEquals("Ex Ample", employee.getName());
        Assertions.assertEquals("Tester", employee.getJobName());
        Assertions.assertEquals("EXEMPLARY", employee.getSalaryGrade());
        Assertions.assertEquals("Testing", employee.getDepartment());
    }
}