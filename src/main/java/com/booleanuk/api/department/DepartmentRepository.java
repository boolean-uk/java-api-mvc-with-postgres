package com.booleanuk.api.department;

import com.booleanuk.api.department.Department;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DepartmentRepository {
    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public DepartmentRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }


    public List<Department> getALl() throws SQLException{
        List<Department> departments = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments");
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Department theDepartment = new Department(results.getInt("id"), results.getString("name"),
                    results.getString("location"));
            departments.add(theDepartment);
        }
        return departments;
    }

    public Department get(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments WHERE id= ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;
        if (results.next()) {
            department = new Department(results.getInt("id"), results.getString("name"),
                    results.getString("location"));
        }
        return department;
    }

    public Department update(int id, Department department) throws SQLException{
        String SQL = "UPDATE Departments " +
                "SET name = ?, " +
                "location = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setInt(3, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        System.out.println(rowsAffected);
        if (rowsAffected > 0){
            updatedDepartment = this.get(id);
        }
        return updatedDepartment;
    }

    public Department delete(int id) throws SQLException {
        String SQL = "DELETE FROM Departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Department deletedDepartment = null;
        deletedDepartment = this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0){
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException{
        String SQL = "INSERT INTO Departments (name, location) VALUES (?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if(rowsAffected > 0){
            try(ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()){
                    newId = rs.getInt(1);
                }
            } catch (Exception e){
                System.out.println("Oh no: " + e);
            }
            department.setId(newId);
        } else {
            department = null;
        }
        return department;
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource() {
        String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }
}
