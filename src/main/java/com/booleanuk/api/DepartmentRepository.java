package com.booleanuk.api;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DepartmentRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String bdDatabase;
    private Connection connection;

    public DepartmentRepository() throws SQLException {
        getDatabaseCredentials();
        dataSource = createDataSource();
        connection = dataSource.getConnection();
    }

    public List<Department> getAll() throws SQLException{
        List<Department> dpts = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM departments;");
        ResultSet results = statement.executeQuery();

        while (results.next()){
            Department theDepartment = new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location")
            );
            dpts.add(theDepartment);
        }

        return dpts;
    }

    public Department getOne(int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM departments WHERE id = ?;");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;

        if (results.next()){
            department = new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location")
            );
        }

        return department;
    }

    public Department update(int id, Department department) throws SQLException{
        String sql = "UPDATE departments " +
                "SET name = ?, " +
                "location = ?, " +
                "WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setInt(3, id);
        statement.executeUpdate();

        return getOne(id);
    }

    public Department delete(int id) throws SQLException{
        String sql = "DELETE FROM departments WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        Department deleted = getOne(id);

        statement.setInt(1, id);
        statement.executeUpdate();

        return deleted;
    }

    public Department add(Department department) throws SQLException{
        String SQL = "INSERT INTO departments " +
                "(name, location) " +
                "VALUES (?, ?);";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

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

    private void getDatabaseCredentials(){
        try {
            InputStream input = new FileInputStream("src/main/resources/config.properties");
            Properties prop = new Properties();

            prop.load(input);
            dbUser = prop.getProperty("db.user");
            dbURL = prop.getProperty("db.url");
            dbPassword = prop.getProperty("db.password");
            bdDatabase = prop.getProperty("db.database");

        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private DataSource createDataSource(){
        String url = "jdbc:postgresql://" + dbURL +
                ":5432/" + bdDatabase +
                "?user=" + dbUser +
                "&password=" + dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);

        return dataSource;
    }
}
