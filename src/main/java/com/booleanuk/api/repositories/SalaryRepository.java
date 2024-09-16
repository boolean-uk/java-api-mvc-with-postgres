package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Salary;
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

public class SalaryRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbUrl;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;


    public SalaryRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> allSalaries = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");
        ResultSet results = statement.executeQuery();
        System.out.println(results);

        while (results.next()){
            Salary theSalary = new Salary(results.getInt("id"),
                    results.getString("grade"),
                    results.getInt("min_salary"),
                    results.getInt("max_salary"));
            allSalaries.add(theSalary);
        }
        return allSalaries;
    }


    public Salary get(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries WHERE id= ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if (results.next()){
            salary = new Salary(results.getInt("id"),
                    results.getString("grade"),
                    results.getInt("min_salary"),
                    results.getInt("max_salary"));
        }
        return salary;
    }

    public Salary update(int id, Salary salary) throws SQLException{
        String SQL = "UPDATE salaries " +
                "SET grade = ?, "+
                "min_salary = ?, "+
                "max_salary = ? "+
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMin_salary());
        statement.setInt(3, salary.getMax_salary());
        statement.setInt(4, id);

        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0){
            updatedSalary = this.get(id);
        }
        return updatedSalary;
    }

    public Salary delete(int id) throws SQLException{
        String SQL = "DELETE FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Salary deletedSalary =  this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0){
            deletedSalary = null;
        }
        return deletedSalary;
    }

    public Salary add(Salary salary) throws SQLException{
        String SQL = "INSERT INTO salaries (grade,min_salary,max_salary) VALUES (?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMin_salary());
        statement.setInt(3, salary.getMax_salary());
        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if (rowsAffected > 0){
            try (ResultSet resultSet = statement.getGeneratedKeys()){
                if (resultSet.next()){
                    newId = resultSet.getInt(1);
                }
            }
            catch (Exception e){
                System.out.println("Can't add salary: " +e);
            }
            salary.setId(newId);
        }else {
            salary = null;
        }
        return salary;
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
