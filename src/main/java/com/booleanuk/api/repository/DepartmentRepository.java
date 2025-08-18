package com.booleanuk.api.repository;

import com.booleanuk.api.model.Department;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DepartmentRepository {





    public DataSource dataSource;
    private Connection connection;

    private String dbUser;
    private String dbUrl;
    private String dbPassword;
    private String dbDatabase;

    public DepartmentRepository() throws SQLException {
        getDatabaseCredentials();
        this.dataSource = createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    private void getDatabaseCredentials(){
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")){
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbUrl = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        }catch (Exception e){

            System.err.println("Oops:  " + e);
        }
    }


    private DataSource createDataSource(){
        final String url = "jdbc:postgresql://" + this.dbUrl + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }



    public List<Department> getAll() throws SQLException  {
        List<Department> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Department employee = new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location")
            );

            everyone.add(employee);
        }
        return everyone;
    }


    public Department getDepartment(int id)throws SQLException {

        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments WHERE ID = ?");
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? new Department(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("location")) : null;
    }

    public Department add(Department salaryGrade) throws SQLException {
        String SQL = "INSERT INTO departments(name, location) VALUES(?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salaryGrade.getName());
        statement.setString(2, salaryGrade.getLocation());
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
            salaryGrade.setId(newId);
        } else {
            salaryGrade = null;
        }
        return salaryGrade;
    }



    public Department update(int id, Department salaryGrade) throws SQLException {
        String SQL = "UPDATE departments " +
                "SET name = ? ," +
                "location = ? ," +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salaryGrade.getName());
        statement.setString(2, salaryGrade.getLocation());
        statement.setLong(3, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.getDepartment(id);
        }
        return updatedEmployee;
    }




    public Department delete(int id) throws SQLException {
        String SQL = "DELETE FROM salaryGrade WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the Employee we're deleting before we delete them
        Department deletedDepartment = null;
        deletedDepartment = this.getDepartment(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the employee we're deleting if we didn't delete them
            deletedDepartment = null;
        }
        return deletedDepartment;
    }






}
