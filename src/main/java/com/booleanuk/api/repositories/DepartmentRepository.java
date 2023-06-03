package com.booleanuk.api.repositories;

import com.booleanuk.api.data.DbAccess;
import com.booleanuk.api.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
@Repository
public class DepartmentRepository {
    @Autowired
    private DbAccess dbAccess;

    public DepartmentRepository(DbAccess dbAccess) throws SQLException {
        this.dbAccess = dbAccess;
    }

    public List<Department> getAll() throws SQLException {
        List<Department> everyone = new ArrayList<>();
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(
                "SELECT * FROM Department ");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Department theSalary = new Department(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("location"));
            everyone.add(theSalary);
        }
        return everyone;
    }

    public Department get(long id) throws SQLException {
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(
                "SELECT * FROM Department WHERE id = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;
        if (results.next()) {
            department = new Department(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("location"));
        }
        return department;
    }
    //
    public Department update(long id, Department department) throws SQLException {
        String SQL = "UPDATE Department " +
                "SET name = ? ," +
                "location = ?  " +
                "WHERE id = ? ";
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setLong(3, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0) {

            updatedDepartment = this.get(id);
        }
        return updatedDepartment;
    }

    public Department delete(long id) throws SQLException {
        String SQL = "DELETE FROM Department WHERE id = ?";
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(SQL);
        // Get the department we're deleting before we delete them
        Department deletedDepartment = null;
        deletedDepartment = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the department we're deleting if we didn't delete them
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException {
        String SQL = "INSERT INTO Department(name,location)" +
                "VALUES (?, ?)";
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());

        int rowsAffected = statement.executeUpdate();
        long newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getLong(1);
                }
            } catch (Exception e) {
                System.out.println("Oops: " + e);
            }
            department.setId(newId);
        } else {
            department = null;
        }
        return department;
    }
}
