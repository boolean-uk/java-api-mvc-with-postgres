package com.booleanuk.api.salary;


import com.booleanuk.api.employee.Employee;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalaryRepository {
    DataSource dataSource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDataBase;
    Connection connection;

    public SalaryRepository() throws SQLException {
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

    public List<Salary> getAll() throws SQLException{
        List<Salary> salaries = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salary");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            Salary salary = new Salary(resultSet.getInt("id"),
                    resultSet.getString("grade"), resultSet.getInt("minSalary"),
                    resultSet.getInt("maxSalary"));
            salaries.add(salary);
        }
        return salaries;
    }

    public Salary get(int id) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM salary WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Salary salary = null;
        if (resultSet.next()) {
            salary = new Salary(
                    resultSet.getInt("id"),
                    resultSet.getString("grade"),
                    resultSet.getInt("minSalary"),
                    resultSet.getInt("maxSalary"));
        }
        return salary;
    }

    public Salary updated(int id, Salary salary) throws SQLException{
        String SQL = "UPDATE salary " + "SET grade = ? ," + "minSalary = ? ," + "maxSalary = ? ," + "WHERE id = ? ,";
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
        preparedStatement.setString(1, salary.getGrade());
        preparedStatement.setInt(2, salary.getMinSalary());
        preparedStatement.setInt(3, salary.getMaxSalary());
        preparedStatement.setInt(4, id);
        int rows = preparedStatement.executeUpdate();
        Salary updated = null;
        if(rows > 0){
            updated = this.get(id);
        }
        return updated;
    }

    public Salary add(Salary salary) throws SQLException{
        String SQL = "INSERT INTO salary(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, salary.getGrade());
        preparedStatement.setInt(2, salary.getMinSalary());
        preparedStatement.setInt(3, salary.getMaxSalary());
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
            salary.setId(newID);
        } else {
            salary = null;
        }
        return salary;
    }


    public Salary delete(int id) throws SQLException{
        String SQL = "DELETE FROM salary WHERE id = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
        preparedStatement.setInt(1, id);
        Salary delete = this.get(id);
        int affected = preparedStatement.executeUpdate();
        if(affected == 0){
            delete = null;
        }
        return delete;
    }
}
