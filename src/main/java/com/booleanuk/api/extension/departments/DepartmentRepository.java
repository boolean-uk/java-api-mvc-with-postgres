package com.booleanuk.api.extension.departments;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DepartmentRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
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

        while(results.next())   {
            Department department = new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location")
            );
            departments.add(department);
        }
        return departments;
    }

    public Department getOne(int id) throws SQLException {
        String SQL = "SELECT * FROM departments WHERE id= ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, id);

        ResultSet results = statement.executeQuery();

        Department department = null;
        if(results.next()){
            department = new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location")
            );
        }
        return department;
    }

    public Department add(Department department) throws SQLException{
        String SQL = "INSERT INTO departments " +
                "(name, location) " +
                "VALUES (?, ?)";

        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());

        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if(rowsAffected > 0)    {
            try(ResultSet rs = statement.getGeneratedKeys())   {
                if(rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println(":( " + e);
            }
            department.setId(newId);
        }   else department = null;
        return department;
    }

    public Department update(int id, Department department) throws SQLException {
        String SQL = "UPDATE departments " +
                "SET name= ?," +
                "location= ?" +
                "WHERE id= ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setInt(3, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if(rowsAffected > 0)    {
            updatedDepartment = this.getOne(id);
        }
        return updatedDepartment;
    }

    public Department delete(int id) throws SQLException {
        String SQL = "DELETE FROM departments WHERE id= ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, id);
        Department deletedDepartment = this.getOne(id);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected == 0)   {
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public void getDatabaseCredentials()    {
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            // Properties : Represents a persistent set of properties.
            //  Can be saved to a stream or loaded from a stream
            //  Each key and its value in the property list is a string
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.URL");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch (Exception e)   {
            System.out.println("Failed to open file: " + e);
        }
    }

    public DataSource createDataSource()    {
        String url = "jdbc:postgresql://" + this.dbURL
                + ":5432/"     + this.dbDatabase
                + "?user="     + this.dbUser
                + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }
}
