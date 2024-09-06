package com.booleanuk.api.departments;
import com.booleanuk.api.ConnectToDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentsRepository {
   private ConnectToDatabase connectToDatabase;

    public DepartmentsRepository() throws SQLException {
        connectToDatabase = new ConnectToDatabase();
    }

    public List<Departments> getAll () throws SQLException {
        List<Departments> everyOne = new ArrayList<>();
        PreparedStatement statement = this.connectToDatabase.getConnection().
                prepareStatement("SELECT * FROM Departments");
        ResultSet results = statement.executeQuery();
        while(results.next()) {
            Departments departments = new Departments(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location"));
            everyOne.add(departments);
        }
        return everyOne;
    }

    public Departments getOne (int id)  throws SQLException {
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(
                "SELECT * FROM Departments WHERE id= ?");
        statement.setInt(1, id);

        ResultSet results = statement.executeQuery();
        Departments departments = null;
        if(results.next()) {
            departments = new Departments(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location"));
            return departments;
        }
        return departments;
    }

    public Departments update(int id, Departments departments) throws SQLException {
        String SQL = "UPDATE Departments " +
                "SET name = ? ," +
                "location = ? ," +
                "WHERE id = ? ";
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(SQL);
        statement.setString(1, departments.getName());
        statement.setString(2, departments.getLocation());
        statement.setInt(3, id);
        int rowsAffected = statement.executeUpdate();
        Departments updatedDepartments = null;
        if(rowsAffected > 0) {
            updatedDepartments = this.getOne(id);
        }
        return updatedDepartments;
    }

    public Departments delete(int id) throws SQLException{
        String SQL = "DELETE FROM Departments WHERE id=?";
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(SQL);
        statement.setInt(1,id);
        Departments deletedDepartments = null;
        deletedDepartments = this.getOne(id);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected == 0 ) {
            deletedDepartments = null;
        }
        return deletedDepartments;
    }

    public Departments add(Departments departments) throws SQLException {
        String SQL = "INSERT INTO Departments (name, location) VALUES (?,?)";
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, departments.getName());
        statement.setString(2, departments.getLocation());
        int rowsAffected = statement.executeUpdate();
        int newID = 0;
        if(rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if(rs.next()) {
                    newID = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            departments.setId(newID);
        } else {
            departments = null;
        }
        return departments;

    }
}
