package com.booleanuk.api;

import com.booleanuk.api.employees.Employee;
import com.booleanuk.api.employees.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class EmployeeTest {
    EmployeeRepository repository;

    @Test
    public void createEmployee() throws SQLException {
        repository = new EmployeeRepository();
        Employee employee = new Employee(
                0,
                "Test",
                "jobName",
                1,
                2
        );

        int currentSize = repository.getAll().size();
        repository.create(employee);
        Assertions.assertEquals(currentSize+1, repository.getAll().size());
        repository.delete(employee.getId());
    }

    @Test
    public void deleteEmployee() throws SQLException {
        repository = new EmployeeRepository();
        Employee employee = new Employee(
                0,
                "Test",
                "jobName",
                2,
                2
        );
        Employee tmp_employee = repository.create(employee);
        int currentSize = repository.getAll().size();
        repository.delete(tmp_employee.getId());
        Assertions.assertEquals(currentSize-1, repository.getAll().size());
    }

    @Test
    public void updateEmployee() throws SQLException {
        repository = new EmployeeRepository();
        Employee employee = new Employee(
                0,
                "UPDATE TEST",
                "jobName",
                2,
                2
        );
        Employee updatedEmployee = repository.update(6, employee);
        Assertions.assertEquals("UPDATE TEST", updatedEmployee.getName());
    }
}
