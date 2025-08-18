package com.booleanuk.api.salaryGrade;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalaryGradeRepository {

    private DataSource dataSource;
    private String dbuser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public SalaryGradeRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            this.dbuser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        } catch (Exception e) {
            System.out.println("Woops: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbuser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<SalaryGrade> getAll() throws SQLException  {
        List<SalaryGrade> all = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM SalaryGrades ");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            SalaryGrade salaryGrade = new SalaryGrade(
                    results.getInt("id"),
                    results.getString("grade")
            );
            all.add(salaryGrade);
        }
        return all;
    }

    public SalaryGrade getOne(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM SalaryGrades WHERE id = ? ");
        statement.setInt(1, id); // 1 = first question mark (and only), id = what question mark represents

        ResultSet results = statement.executeQuery();

        SalaryGrade salaryGrade = null;
        if (results.next()) {
            salaryGrade = new SalaryGrade(
                    results.getInt("id"),
                    results.getString("grade")
            );
        }
        return salaryGrade;
    }

    public SalaryGrade get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM SalaryGrades WHERE id = ? ");
        // Choose set**** matching the datatype of the missing element
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();

        SalaryGrade salaryGrade = null;
        if (results.next()) {
            salaryGrade = new SalaryGrade(
                    results.getInt("id"),
                    results.getString("grade")
            );
        }
        return salaryGrade;
    }

    public SalaryGrade update(int id, SalaryGrade salaryGrade) throws SQLException {
        String SQL = "UPDATE SalaryGrade " +
                "SET grade = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salaryGrade.getGrade());
        statement.setInt(2, id);

        int rowsAffected = statement.executeUpdate();
        SalaryGrade updated = null;
        if (rowsAffected > 0) {
            updated = this.get(id);
        }
        return updated;
    }

    public SalaryGrade delete(int id) throws SQLException {
        String SQL = "DELETE FROM SalaryGrades WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the customer we're deleting before we delete them
        SalaryGrade deleted = null;
        deleted = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deleted = null;
        }
        return deleted;
    }

    public SalaryGrade add(SalaryGrade salaryGrade) throws SQLException {
        String SQL = "INSERT INTO SalaryGrades (grade) VALUES (?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salaryGrade.getGrade());
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
}

