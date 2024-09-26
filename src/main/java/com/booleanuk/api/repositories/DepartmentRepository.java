package com.booleanuk.api.repositories;

import com.booleanuk.api.database.Database;
import com.booleanuk.api.database.PostgresDatabase;
import com.booleanuk.api.models.Department;

import java.util.List;

public class DepartmentRepository {
    public Database db = new PostgresDatabase();

    public List<Department> getAll() {
        return null;
    }

    public Department get(int id) {
        return null;
    }

    public Department update(int id, Department department) {
        return null;
    }

    public Department add(Department department) {
        return null;
    }

    public Department delete(int id) {
        return null;
    }
}
