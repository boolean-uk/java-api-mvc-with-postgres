package com.booleanuk.api.department;

import com.booleanuk.api.salary.Salary;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DepartmentRepository {
    DataSource dataSource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDataBase;
    Connection connection;

    public DepartmentRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();


    }

    private void getDatabaseCredentials() {
        try (InputStream inputStream = new FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            this.dbUser = properties.getProperty("db.user");
            this.dbURL = properties.getProperty("db.url");
            this.dbPassword = properties.getProperty("db.password");
            this.dbDataBase = properties.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Oh no: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDataBase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Department> getAll() throws SQLException{
        List<Department> departments = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM department");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            Department department = new Department(resultSet.getInt("id"),
                    resultSet.getString("name"), resultSet.getString("location"));
            departments.add(department);
        }
        return departments;
    }

    public Department get(int id) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM department WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Department department = null;
        if (resultSet.next()) {
            department = new Department(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("location"));
        }
        return department;
    }

    public Department updated(int id, Department department) throws SQLException{
        String SQL = "UPDATE department " + "SET name = ? ," + "location = ? ," + "WHERE id = ? ,";
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
        preparedStatement.setString(1, department.getName());
        preparedStatement.setString(2, department.getLocation());
        preparedStatement.setInt(3, id);
        int rows = preparedStatement.executeUpdate();
        Department updated = null;
        if(rows > 0){
            updated = this.get(id);
        }
        return updated;
    }

    public Department delete(int id) throws SQLException{
        String SQL = "DELETE FROM department WHERE id = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
        preparedStatement.setInt(1, id);
        Department delete = this.get(id);
        int affected = preparedStatement.executeUpdate();
        if(affected == 0){
            delete = null;
        }
        return delete;
    }

    public Department add(Department department) throws SQLException{
        String SQL = "INSERT INTO department(name, location, maxDepartment) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, department.getName());
        preparedStatement.setString(2, department.getLocation());
        int affected = preparedStatement.executeUpdate();
        int newID = 0;
        if(affected > 0){
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if(resultSet.next()){
                    newID = resultSet.getInt(1);
                }
            } catch (Exception e){
                System.out.println("Oops: " +  e);
            }
            department.setId(newID);
        } else {
            department = null;
        }
        return department;
    }
}
