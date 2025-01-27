package com.booleanuk.api.department;

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

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Problem getDatabaseCredentials: " + e);
        }
    }

    private DataSource createDataSource() {
//        String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase
//                + "?user=" + this.dbUser + "&password=" + this.dbPassword;
//        PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setUrl(url);
//        return dataSource;
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        System.out.println(url);
        dataSource.setUrl(url);
        return dataSource;
    }

    public Department add(Department department) throws SQLException {
        String sql = """
                INSERT INTO departments\
                (name, location)\
                VALUES\
                (?, ?)
                """;
        PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, department.getName());
        ps.setString(2, department.getLocation());
        int nRowsAffected = ps.executeUpdate();
        int newId = -1;
        if (nRowsAffected > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Oops in add: " + e);
            }
            department.setId(newId);
            return department;
        }
        return null;
    }

    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        PreparedStatement ps = this.connection.prepareStatement(
                "SELECT * FROM departments;");

        ResultSet results = ps.executeQuery();

        while (results.next()) {
            Department d = new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location"));
            departments.add(d);
        }

        return departments;
    }

    public Department getOne(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(
                "SELECT * FROM departments WHERE id = ?;");
        ps.setInt(1, id);

        ResultSet results = ps.executeQuery();
        if (results.next()) {
            return new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location"));
        }
        return null;
    }

    public Department updateOne(int id, Department department) throws SQLException {
        String sql = """
                UPDATE departments
                SET name = ?,
                location = ?
                WHERE id = ?;
                """;

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setString(1, department.getName());
        ps.setString(2, department.getLocation());
        ps.setInt(3, id);

        int nRowsAffected = ps.executeUpdate();
        if (nRowsAffected > 0) {
            return this.getOne(id);
        }
        return null;
    }

    public Department deleteOne(int id) throws SQLException {
        String sql = """
                DELETE FROM departments
                WHERE id = ?;
                """;
        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setInt(1, id);

        Department departmentToDelete = this.getOne(id);

        int nRowsAffected = ps.executeUpdate();
        if (nRowsAffected > 0) {
            return departmentToDelete;
        }
        return null;
    }
}
