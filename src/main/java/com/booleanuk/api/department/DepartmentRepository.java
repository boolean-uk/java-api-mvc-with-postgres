package com.booleanuk.api.department;

import com.booleanuk.api.employee.Employee;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
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

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch(Exception e) {
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource() {
        // The url specifies the address of our database along with username and password credentials
        // you should replace these with your own username and password
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Department> getAll() throws SQLException  {
        List<Department> departments = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Department theDepartment = new Department(results.getLong("id"), results.getString("name"), results.getString("location"));
            departments.add(theDepartment);
        }
        return departments;
    }

    public Department get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments WHERE id = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;
        if (results.next()) {
            department = new Department(results.getLong("id"), results.getString("name"), results.getString("location"));
        }
        return department;
    }

    public Department update(long id, Department department) throws SQLException {
        String SQL = "UPDATE Departments " +
                "SET name = ? ," +
                "location = ? ," +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setLong(3, id);
        int rowsAffected = statement.executeUpdate();
        Department updateDepartment = null;
        if (rowsAffected > 0) {
            updateDepartment = this.get(id);
        }
        return updateDepartment;
    }

    public Department delete(long id) throws SQLException {
        String SQL = "DELETE FROM Departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the customer we're deleting before we delete them
        Department deletedDepartment = null;
        deletedDepartment = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException {
        if (this.getByName(department.getName()) != null) {
            System.out.println("Department already exists. Cannot add salary.");
            return null;
        }
        String SQL = "INSERT INTO Departments(name, location) VALUES(?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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

    public Department getByName(String name) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments WHERE name = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setString(1, name);
        ResultSet results = statement.executeQuery();
        Department department = null;
        if (results.next()) {
            department = new Department(results.getLong("id"), results.getString("name"), results.getString("location"));
        }
        return department;
    }
}
