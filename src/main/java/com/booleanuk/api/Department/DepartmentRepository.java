package com.booleanuk.api.Department;

import com.booleanuk.api.Salary.Salary;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    String dbUrl;
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
            this.dbUrl = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        } catch (Exception e) {
            System.out.println("oopsie: " + e);
        }

    }


    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbUrl + ":5432/" + this.dbDatabase +
                "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }


    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments");

        ResultSet  results = statement.executeQuery();

        while (results.next()) {
            Department department = new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location")
            );
            departments.add(department);
        }
        return departments;
    }

    public Department get(int id)  {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement("SELECT * FROM Departments WHERE id = ?");

        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Department depertment = null;
        if(results.next()) {
            depertment =  new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location")
            );
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
            return depertment;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }

    }

    public Department update(int id, Department department)  {
        String SQL = "UPDATE Departments " +
                "SET name = ? ," +
                "location = ? " +
                "WHERE id = ? ";
        try {


            PreparedStatement statement = this.connection.prepareStatement(SQL);
            statement.setString(1, department.getName());
            statement.setString(2, department.getLocation());
            statement.setInt(3, id);

            statement.executeUpdate();


        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "could not update the department, please check all required fields are correct");

        }
        return this.get(id);

    }

    public Department delete(int id) throws SQLException {
        String SQL = "DELETE FROM Departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, id);

        Department deletedDepartment = null;
        deletedDepartment = this.get(id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            return this.get(id);
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException {
        String SQL = "INSERT INTO Departments (name, location) VALUES (?,?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        int rowsAffected = statement.executeUpdate();
        int newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if(rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("oops: " + e);
            }
            department.setId(newId);
        } else {
            department = null;
        }
        return department;
    }
}
