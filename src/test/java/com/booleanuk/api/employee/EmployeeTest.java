package com.booleanuk.api.employee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class EmployeeTest {
    @Test
    public void testEmployee() {
        Employee e = new Employee(1, "Marit", "Developer", "1", "Academy");
        Assertions.assertEquals(1, e.getId());
        Assertions.assertEquals("Marit", e.getName());
        Assertions.assertEquals("Developer", e.getJobName());
        Assertions.assertEquals("1", e.getSalaryGrade());
        Assertions.assertEquals("Academy", e.getDepartment());
    }

    @Test
    public void testToString() {
        Employee e = new Employee(1, "Marit", "Developer", "1", "Academy");
        Assertions.assertEquals("1 - Marit - Developer - 1 - Academy", e.toString());
    }
}