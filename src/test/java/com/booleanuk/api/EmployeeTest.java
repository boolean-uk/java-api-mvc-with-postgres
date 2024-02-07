package com.booleanuk.api;

import com.booleanuk.api.models.Employee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void testConstructorAndGetters() {
        int id = 1;
        String name = "John Doe";
        String jobName = "Software Engineer";
        String salaryGrade = "65";
        String department = "IT";

        Employee employee = new Employee(id, name, jobName, salaryGrade, department);

        assertEquals(id, employee.getId());
        assertEquals(name, employee.getName());
        assertEquals(jobName, employee.getJobName());
        assertEquals(salaryGrade, employee.getSalaryGrade());
        assertEquals(department, employee.getDepartment());
    }

    @Test
    public void testSetId() {
        Employee employee = new Employee(1, "John Doe", "Software Engineer", "A", "IT");
        int newId = 2;

        employee.setId(newId);

        assertEquals(newId, employee.getId());
    }

    @Test
    public void testToString() {
        Employee employee = new Employee(1, "John Doe", "Software Engineer", "A", "IT");

        String expectedString = "1 : John Doe | IT";
        String actualString = employee.toString();

        assertEquals(expectedString, actualString);
    }
}