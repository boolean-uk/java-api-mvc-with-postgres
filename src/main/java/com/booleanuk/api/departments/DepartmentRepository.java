package com.booleanuk.api.departments;

import com.booleanuk.api.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {
    private MyConnection connection;

    public DepartmentRepository() throws SQLException{
        this.connection = new MyConnection();
    }
    public List<Department> getAll() throws SQLException{
        List<Department> all = new ArrayList<>();
        PreparedStatement statement = this.connection.getConnection().prepareStatement("SELECT * FROM Departments");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            all.add(new Department(result.getInt("id"),result.getString("name"),result.getString("location")));
        }
        return all;
    }
    public Department getDepartment(int id)throws SQLException{
        PreparedStatement statement = this.connection.getConnection().prepareStatement("SELECT * FROM Departments WHERE id=?");
        statement.setInt(1,id);
        ResultSet result = statement.executeQuery();
        Department temp = null;
        if(result.next()){
            temp = new Department(result.getInt("id"),result.getString("name"),result.getString("location"));
        }
        return temp;
    }
    public Department updateDepartment(int id, Department department) throws SQLException {
        String SQl = "UPDATE departments SET name = ? , location = ? WHERE id = ?";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(SQl);
        statement.setString(1,department.getName());
        statement.setString(2,department.getLocation());
        statement.setInt(3,id);
        int rowsAffected = statement.executeUpdate();
        //checking for faulty body.
        if(department.getName() == null || department.getLocation()==null){
            return new Department(-1,"N/A","N/A");
        }
        Department updatedDepartment = null;
        if(rowsAffected>0){
            updatedDepartment = this.getDepartment(id);
        }
        return updatedDepartment;
    }
    public Department deleteDepartment(int id) throws SQLException{
        PreparedStatement statement = this.connection.getConnection().prepareStatement("DELETE FROM Departments WHERE id =?");
        Department deletedDepartment = this.getDepartment(id);
        statement.setInt(1,id);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected==0){
            deletedDepartment = null;
        }
        return deletedDepartment;

    }
    public Department addDepartment(Department department) throws SQLException {
        String SQL = "INSERT INTO Departments(name, location) VALUES(?, ?)";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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
                System.out.println("Oops: " + e);
            }
            department.setId(newId);
        } else {
            department = null;
        }
        return department;
    }
}
