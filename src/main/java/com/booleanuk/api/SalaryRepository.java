package com.booleanuk.api;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalaryRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public SalaryRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")){
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        } catch (Exception e) {
            System.out.println("This is made by Dave "+ e);
        }
    }

    private DataSource createDataSource(){
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public void connectToDatabase() throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            String id = "" + results.getInt("id");
            String grade = results.getString("grade");
            System.out.println(id + " - " + grade + " - ");
        }
    }
    public List<Salary> getAll() throws SQLException  {
        List<Salary> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Salary theSalary = new Salary(results.getInt("id"), results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
            everyone.add(theSalary);
        }
        return everyone;
    }

    public Salary get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries WHERE id = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Salary customer = null;
        if (results.next()) {
            customer = new Salary(results.getInt("id"), results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
        }
        return customer;
    }

    public Salary update(int id, Salary customer) throws SQLException {
        String SQL = "UPDATE Salaries " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, customer.getGrade());
        statement.setInt(2, customer.getMinSalary());
        statement.setInt(3, customer.getMaxSalary());
        statement.setInt(4, id);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.get(id);
        }
        return updatedSalary;
    }

    public Salary delete(int id) throws SQLException {
        String SQL = "DELETE FROM Salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the customer we're deleting before we delete them
        Salary deletedSalary = null;
        deletedSalary = this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedSalary = null;
        }
        return deletedSalary;
    }

    public Salary add(Salary customer) throws SQLException {
        String SQL = "INSERT INTO Salaries(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, customer.getGrade());
        statement.setInt(2, customer.getMinSalary());
        statement.setInt(3, customer.getMaxSalary());
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
            customer.setId(newId);
        } else {
            customer = null;
        }
        return customer;
    }

}

