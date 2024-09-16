package com.booleanuk.api.salaryGrade;


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

public class SalaryGradeRepository {
    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public SalaryGradeRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }


    public List<SalaryGrade> getALl() throws SQLException{
        List<SalaryGrade> salaryGrades = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM SalaryGrades");
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            SalaryGrade theSalaryGrade = new SalaryGrade(results.getInt("id"), results.getString("grade"),
                    results.getInt("minsalary"), results.getInt("maxsalary"));
            salaryGrades.add(theSalaryGrade);
        }
        return salaryGrades;
    }

    public SalaryGrade get(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM SalaryGrades WHERE id= ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        SalaryGrade salaryGrade = null;
        if (results.next()) {
            salaryGrade = new SalaryGrade(results.getInt("id"), results.getString("grade"),
                    results.getInt("minsalary"), results.getInt("maxsalary"));
        }
        return salaryGrade;
    }

    public SalaryGrade update(int id, SalaryGrade salaryGrade) throws SQLException{
        String SQL = "UPDATE SalaryGrades " +
                "SET grade = ?, " +
                "minsalary = ?, " +
                "maxsalary = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salaryGrade.getGrade());
        statement.setInt(2, salaryGrade.getMinSalary());
        statement.setInt(3, salaryGrade.getMaxSalary());
        statement.setInt(4, id);
        int rowsAffected = statement.executeUpdate();
        SalaryGrade updatedSalaryGrade = null;
        System.out.println(rowsAffected);
        if (rowsAffected > 0){
            updatedSalaryGrade = this.get(id);
        }
        return updatedSalaryGrade;
    }

    public SalaryGrade delete(int id) throws SQLException {
        String SQL = "DELETE FROM SalaryGrades WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        SalaryGrade deletedSalaryGrade = null;
        deletedSalaryGrade = this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0){
            deletedSalaryGrade = null;
        }
        return deletedSalaryGrade;
    }

    public SalaryGrade add(SalaryGrade salaryGrade) throws SQLException{
        String SQL = "INSERT INTO SalaryGrades (grade, minsalary, maxsalary) VALUES (?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salaryGrade.getGrade());
        statement.setInt(2, salaryGrade.getMinSalary());
        statement.setInt(3, salaryGrade.getMaxSalary());
        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if(rowsAffected > 0){
            try(ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()){
                    newId = rs.getInt(1);
                }
            } catch (Exception e){
                System.out.println("Oh no: " + e);
            }
            salaryGrade.setId(newId);
        } else {
            salaryGrade = null;
        }
        return salaryGrade;
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
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource() {
        String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }
}
