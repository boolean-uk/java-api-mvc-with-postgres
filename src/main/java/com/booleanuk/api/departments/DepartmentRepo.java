package com.booleanuk.api.departments;

import com.booleanuk.api.employee.Employee;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class DepartmentRepo {

    private final Connection connection;

    public DepartmentRepo(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();

    }
    public ArrayList<Department> getAllData() throws SQLException {
        ArrayList<Department> departments = new ArrayList<>();
        try (
                PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments");
                ResultSet results = statement.executeQuery();
        ) {
            while (results.next()) {
                Department theDepartment = new Department(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("location")
                );
                departments.add(theDepartment);
            }
        }
        return departments;
    }

    public Department getOne(int departmentId) throws SQLException {
        Department theDepartment = null;
        String sql = "SELECT * FROM departments WHERE id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, departmentId);
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    theDepartment = new Department(
                            results.getInt("id"),
                            results.getString("name"),
                            results.getString("location")
                    );
                }
            }
        }
        return theDepartment;
    }

    public Department add(Department department) throws SQLException {
        String sql = "INSERT INTO DEPARTMENTS (name, location) VALUES (?, ?) RETURNING id";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, department.getName());
            statement.setString(2, department.getLocation());
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    int generateId = results.getInt(1);
                    department.setId(generateId);
                }
            }
        }
        return department;
    }

    public Department update(int id, Department department) throws SQLException {
        String sql = "UPDATE departments SET name = ?, location = ? WHERE id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, department.getName());
            statement.setString(2, department.getLocation());
            statement.setInt(5, id);
            statement.executeUpdate();

            return getOne(department.getId());
        }
    }

    public Department delete (int id) throws SQLException {
        Department department = getOne(id);

        if (department == null) {
            return null;
        }
        String sql = "DELETE FROM departments WHERE id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();

            return (affectedRows > 0) ? department : null;
        }
    }

}
