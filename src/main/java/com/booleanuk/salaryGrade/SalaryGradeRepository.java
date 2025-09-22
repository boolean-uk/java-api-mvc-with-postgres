package com.booleanuk.salaryGrade;

import com.booleanuk.employee.Employee;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class SalaryGradeRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public SalaryGradeRepository(DataSource dataSource, String dbUser, String dbURL, String dbPassword, String dbDatabase) throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        } catch (IOException e) {
            System.err.println("Error opening config.properties file " + e);
        }
    }

    public List<SalaryGrade> getAll() throws SQLException  {
        List<SalaryGrade> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM SALARY_GRADES");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            SalaryGrade theSalaryGrade = new SalaryGrade(
                    results.getInt("id"),
                    results.getString("grade"));
            everyone.add(theSalaryGrade);
        }
        return everyone;
    }

    public SalaryGrade getOne(int id) throws SQLException  {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM SALARY_GRADES WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        SalaryGrade theSalaryGrade = null;
        if (results.next()) {
            theSalaryGrade = new SalaryGrade(
                    results.getInt("id"),
                    results.getString("grade"));

        }
        return theSalaryGrade;
    }

    public SalaryGrade add(SalaryGrade salaryGrade) throws SQLException {
        String SQL = "INSERT INTO SALARYGRADES(grade) VALUES(?)";
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

    public SalaryGrade delete(int id) throws SQLException {
        SalaryGrade deletedSalaryGrade = null;
        deletedSalaryGrade = this.getOne(id);

        String SQL = "DELETE FROM SALARYGRADES WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalaryGrade = null;
        }
        return deletedSalaryGrade;
    }

    public SalaryGrade update(int id, SalaryGrade salaryGrade) throws SQLException {
        String SQL = "UPDATE SALARYGRADES SET grade = ? WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salaryGrade.getGrade());
        statement.setLong(2, id);
        int rowsAffected = statement.executeUpdate();
        SalaryGrade updatedSalaryGrade = null;
        if (rowsAffected > 0) {
            updatedSalaryGrade = this.getOne(id);
        }
        return updatedSalaryGrade;
    }



}
