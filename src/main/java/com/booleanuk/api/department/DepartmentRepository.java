package com.booleanuk.api.department;

import com.booleanuk.api.employee.Employee;
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
    DataSource dataSource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public DepartmentRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
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
            System.out.println("Database Credentials Failed To Load" + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource1 = new PGSimpleDataSource();
        dataSource1.setURL(url);
        return dataSource1;
    }

    public List<Department> getAllDepartments() throws SQLException {
        List<Department> departmentList = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments");
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            Department department = new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location")
            );
            departmentList.add(department);
        }
        return departmentList;
    }

    public Department getOneDepartment(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM department WHERE id = ?");
        statement.setInt(1,id);
        ResultSet result = statement.executeQuery();
        Department department = null;
        if (result.next()) {
            department = new Department(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("location")
            );
        }
        return department;
    }
}
