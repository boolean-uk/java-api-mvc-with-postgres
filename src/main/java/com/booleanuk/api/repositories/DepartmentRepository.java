package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Department;
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
    private DataSource dataSource;
    private String dbUser;
    private String dbUrl;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;


    public DepartmentRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments");
        ResultSet results = statement.executeQuery();
        System.out.println(results);

        while (results.next()){
            Department theDepartment = new Department(results.getInt("id"),
                    results.getString("name"),
                    results.getString("location"));
            departments.add(theDepartment);
        }
        return departments;
    }


    public Department get(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments WHERE id= ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;
        if (results.next()){
            department = new Department(results.getInt("id"),
                    results.getString("name"),
                    results.getString("location"));
        }
        return department;
    }

    public Department update(int id, Department department) throws SQLException{
        String SQL = "UPDATE departments " +
                "SET name = ?, "+
                "location = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setInt(3, id);

        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0){
            updatedDepartment = this.get(id);
        }
        return updatedDepartment;
    }

    public Department delete(int id) throws SQLException{
        String SQL = "DELETE FROM departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Department deletedDepartment =  this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0){
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException{
        String SQL = "INSERT INTO departments (name,location) VALUES (?,?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if (rowsAffected > 0){
            try (ResultSet resultSet = statement.getGeneratedKeys()){
                if (resultSet.next()){
                    newId = resultSet.getInt(1);
                }
            }
            catch (Exception e){
                System.out.println("Can't add department: " +e);
            }
            department.setId(newId);
        }else {
            department = null;
        }
        return department;
    }

    private void getDatabaseCredentials(){
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            this.dbUser = properties.getProperty("db.user");
            this.dbUrl = properties.getProperty("db.url");
            this.dbPassword = properties.getProperty("db.password");
            this.dbDatabase = properties.getProperty("db.database");

        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource(){
        String url = "jdbc:postgresql://" +this.dbUrl+ ":5432/" +this.dbDatabase+
                "?user=" +this.dbUser+ "&password=" +this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }
}
