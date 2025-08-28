package com.booleanuk.api.model.pojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeTest {

    // Some basic tests to verify the functionality of Lombok-generated methods

    @Test
    void testAllArgsConstructorAndGetters() {
        Employee emp = new Employee(1L, "John Doe", "Software Engineer", "G5", "Engineering");
        assertEquals(1L, emp.getId());
        assertEquals("John Doe", emp.getName());
        assertEquals("Software Engineer", emp.getJobName());
        assertEquals("G5", emp.getSalaryGrade());
        assertEquals("Engineering", emp.getDepartment());
    }

    @Test
    void testSetters() {
        Employee emp = new Employee(0L, "", "", "", "");
        emp.setId(2L);
        emp.setName("Jane Smith");
        emp.setJobName("Product Manager");
        emp.setSalaryGrade("G6");
        emp.setDepartment("Product");
        assertEquals(2L, emp.getId());
        assertEquals("Jane Smith", emp.getName());
        assertEquals("Product Manager", emp.getJobName());
        assertEquals("G6", emp.getSalaryGrade());
        assertEquals("Product", emp.getDepartment());
    }

    @Test
    void testEqualsAndHashCode() {
        Employee emp1 = new Employee(3L, "Alice Johnson", "Data Scientist", "G5", "Data Science");
        Employee emp2 = new Employee(3L, "Alice Johnson", "Data Scientist", "G5", "Data Science");
        assertEquals(emp1, emp2);
        assertEquals(emp1.hashCode(), emp2.hashCode());
    }

    @Test
    void testToString() {
        Employee emp = new Employee(4L, "Bob Brown", "UX Designer", "G4", "Design");
        String str = emp.toString();
        assertTrue(str.contains("Bob Brown"));
        assertTrue(str.contains("UX Designer"));
        assertTrue(str.contains("G4"));
        assertTrue(str.contains("Design"));
    }
}
