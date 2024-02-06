package com.booleanuk.api.repository;

import com.booleanuk.api.model.Department;
import com.booleanuk.api.model.Salary;
import com.booleanuk.api.util.UtilClass;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DepartmentRepository {
    private Connection connection;

    public DepartmentRepository() throws SQLException {
        UtilClass util = new UtilClass();
        this.connection = util.getConnection();
    }



    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();


        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Department");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Department salary = new Department(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            departments.add(salary);
        }

        return departments;
    }

    public Department get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Department WHERE departmentId = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Department department = null;
        if (resultSet.next()) {
            department = new Department(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        }
        return department;
    }

    public Department update(int id, Department department) throws SQLException {
        String SQL = "UPDATE Salary " +
                "SET departmentId = ? ," +
                "name = ? ," +
                "location = ? ," +
                "WHERE departmentId = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, department.getDepartmentId());
        statement.setString(2, department.getName());
        statement.setString(3, department.getLocation());
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0) {
            updatedDepartment = this.get(id);
        }
        return updatedDepartment;
    }

    public Department delete(int id) throws SQLException {
        String SQL = "DELETE FROM Department WHERE departmentId = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Department deletedDepartment = null;
        deletedDepartment = this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedDepartment = null;
        }
        return deletedDepartment;

    }

    public Department add(Department department) throws SQLException {
        String SQL = "INSERT INTO Department(departmentId, name, location) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, department.getDepartmentId());
        statement.setString(2, department.getName());
        statement.setString(3, department.getLocation());
        int rowsAffected = statement.executeUpdate();
        int id = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            department.setDepartmentId(id);
        } else {
            department = null;
        }
        return department;
    }
}
