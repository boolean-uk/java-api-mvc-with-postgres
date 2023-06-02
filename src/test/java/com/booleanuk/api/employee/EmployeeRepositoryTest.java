package com.booleanuk.api.employee;

import com.booleanuk.api.setup.DatabaseConnector;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

public class EmployeeRepositoryTest {

    private static DatabaseConnector dbConnector;
    private static EmployeeRepository employeeRepository;

    @BeforeAll
    public static void setup() throws SQLException {
        dbConnector = new DatabaseConnector();
        employeeRepository = new EmployeeRepository(dbConnector);
    }

    @Test
    public void testGetAll() throws SQLException {
        Assertions.assertFalse(employeeRepository.getAll().isEmpty());
    }

    @Test
    public void testGetById() throws SQLException {
        Assertions.assertNull(employeeRepository.getById(8888));
        Employee employee = employeeRepository.getById(1);
        Assertions.assertEquals("John Doe", employee.getName());
    }
}
