package com.booleanuk.api;

import com.booleanuk.api.model.Employee;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeTest {

    @Mock
    private ResultSet mockResultSet;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        int id = 1;
        String name = "John Doe";
        String jobName = "Software Developer";
        String salaryGrade = "A1";
        String department = "IT";

        // When
        employee = new Employee(id, name, jobName, salaryGrade, department);

        // Then
        assertEquals(id, employee.getId());
        assertEquals(name, employee.getName());
        assertEquals(jobName, employee.getJobName());
        assertEquals(salaryGrade, employee.getSalaryGrade());
        assertEquals(department, employee.getDepartment());
    }

    @Test
    void testResultSetConstructor() throws SQLException {
        // Given
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Jane Smith");
        when(mockResultSet.getString("jobName")).thenReturn("Project Manager");
        when(mockResultSet.getString("salaryGrade")).thenReturn("B2");
        when(mockResultSet.getString("department")).thenReturn("Operations");

        // When
        employee = new Employee(mockResultSet);

        // Then
        assertEquals(1, employee.getId());
        assertEquals("Jane Smith", employee.getName());
        assertEquals("Project Manager", employee.getJobName());
        assertEquals("B2", employee.getSalaryGrade());
        assertEquals("Operations", employee.getDepartment());

        // Verify that ResultSet methods were called
        verify(mockResultSet).getInt("id");
        verify(mockResultSet).getString("name");
        verify(mockResultSet).getString("jobName");
        verify(mockResultSet).getString("salaryGrade");
        verify(mockResultSet).getString("department");
    }

    @Test
    void testResultSetConstructorThrowsSQLException() throws SQLException {
        // Given
        when(mockResultSet.getInt("id")).thenThrow(new SQLException("Database error"));

        // When & Then
        assertThrows(SQLException.class, () -> new Employee(mockResultSet));
    }

    @Test
    void testSettersAndGetters() {
        // Given
        employee = new Employee(1, "Initial Name", "Initial Job", "A1", "Initial Dept");

        // When
        employee.setId(2);
        employee.setName("Updated Name");
        employee.setJobName("Updated Job");
        employee.setSalaryGrade("B2");
        employee.setDepartment("Updated Dept");

        // Then
        assertEquals(2, employee.getId());
        assertEquals("Updated Name", employee.getName());
        assertEquals("Updated Job", employee.getJobName());
        assertEquals("B2", employee.getSalaryGrade());
        assertEquals("Updated Dept", employee.getDepartment());
    }
}
