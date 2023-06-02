package com.booleanuk.api.repositories;

import com.booleanuk.api.database.Database;
import com.booleanuk.api.database.PostgresDatabase;
import com.booleanuk.api.models.Salary;

import java.util.List;

public class SalaryRepository {
    public Database db = new PostgresDatabase();

    public List<Salary> getAll() {
        return null;
    }

    public Salary get(int id) {
        return null;
    }

    public Salary update(int id, Salary salary) {
        return null;
    }

    public Salary add(Salary salary) {
        return null;
    }

    public Salary delete(int id) {
        return null;
    }
}
