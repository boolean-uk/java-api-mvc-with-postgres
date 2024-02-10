package com.booleanuk.api.salary;

import com.booleanuk.api.department.Department;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalaryRepository {
    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;


    public SalaryRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }
    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            // Properties used to read value from properties file. Build in java method
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

    public List<Salary> getAll() throws SQLException {
        List<Salary> allSalary = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("select * from salaries");

        ResultSet results = statement.executeQuery();

        while (results.next()){
            Salary theSalary = new Salary(results.getLong("id"), results.getString("grade"), results.getInt("min_salary"), results.getInt("max_salary"));
            allSalary.add(theSalary);
        }
        return allSalary;
    }

    //the sql statement retrieves all columns from the salaries table where the 'id' column matches a parameter (id)
    public Salary get(long id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("select * from salaries where id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if(results.next()){
            salary = new Salary(results.getLong("id"), results.getString("grade"), results.getInt("min_salary"), results.getInt("max_salary"));

        }
        return salary;

    }

    public Salary update(long id, Salary salary) throws SQLException{
        String SQL = "update salaries "+
                "set grade = ? ,"+
                "min_salary = ? ," +
                "max_salary = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        statement.setLong(4, id);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if(rowsAffected > 0){
            updatedSalary = this.get(id);
        }
        return updatedSalary;
    }
    public Salary delete(long id) throws SQLException {
        String SQL = "delete from salaries where id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the customer we're deleting before we delete them
        Salary deletedSalary = null;
        deletedSalary = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedSalary = null;
        }
        return deletedSalary;
    }


    public Salary add(Salary salary) throws SQLException {
        String SQL = "insert into salaries ( grade, min_salary, max_salary) values( ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
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
            salary.setId(newId);
        } else {
            salary = null;
        }
        return salary;
    }
}
