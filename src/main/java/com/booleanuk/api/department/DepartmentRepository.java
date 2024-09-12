package com.booleanuk.api.department;

import com.booleanuk.api.employee.Employee;
import com.booleanuk.api.setup.DatabaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartmentRepository {

    private final Connection connection;

    @Autowired
    public DepartmentRepository(DatabaseConnector dbConnector){
        connection = dbConnector.getConnection();
    }

    public Department create(Department department){
        return null;
    }

    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Department");

        ResultSet resultSet = statement.executeQuery();

        return this.getFromResultSet(resultSet);
    }

    public Department getById(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Department WHERE id=?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        List<Department> departments = this.getFromResultSet(rs);

        if(departments.size() > 0) return departments.get(0);
        return null;
    }

    public Department update(int id, Department department) throws SQLException {
        String SQL = "UPDATE Department " +
                "SET name = ? ," +
                "location = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setInt(3, id);


        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0) {
            updatedDepartment = this.getById(id);
        }
        return updatedDepartment;

    }

    public Department delete(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("DELETE FROM Department WHERE id=?");
        statement.setInt(1, id);

        Department deletedDepartment = this.getById(id);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException {
        String SQL = "INSERT INTO Department (name, location) VALUES(?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        int rowsAffected = statement.executeUpdate();
        int newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e);
            }
            department.setId(newId);
        } else {
            department = null;
        }
        return department;
    }

    private List<Department> getFromResultSet(ResultSet rs) throws SQLException {
        List<Department> departments = new ArrayList<>();
        while (rs.next()){
            Department current = new Department(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"));
            departments.add(current);
        }
        return departments;
    }
}
