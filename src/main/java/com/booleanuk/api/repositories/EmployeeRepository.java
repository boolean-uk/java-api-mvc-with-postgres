package com.booleanuk.api.repositories;

import com.booleanuk.api.database.Database;
import com.booleanuk.api.database.PostgresDatabase;
import com.booleanuk.api.models.Employee;

import java.sql.Connection;
import java.util.List;

public class EmployeeRepository {
    public Database db = new PostgresDatabase();

    public List<Employee> getAll() {
        return null;
    }

    public Employee get(int id) {
        return null;
    }

    public Employee update(int id, Employee employee) {
        return null;
    }

    public Employee add(Employee employee) {
        return null;
    }

    public Employee delete(int id) {
        return null;
    }
}
