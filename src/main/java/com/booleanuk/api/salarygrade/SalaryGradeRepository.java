package com.booleanuk.api.salarygrade;

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
    private String dbUser;
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
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Problem getDatabaseCredentials: " + e);
        }
    }

    private DataSource createDataSource() {
//        String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase
//                + "?user=" + this.dbUser + "&password=" + this.dbPassword;
//        PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setUrl(url);
//        return dataSource;
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        System.out.println(url);
        dataSource.setUrl(url);
        return dataSource;
    }

    public SalaryGrade add(SalaryGrade salaryGrade) throws SQLException {
        String sql = """
                INSERT INTO salary_grades\
                (grade, min_salary, max_salary)\
                VALUES\
                (?, ?, ?);
                """;
        PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, salaryGrade.getGrade());
        ps.setInt(2, salaryGrade.getMinSalary());
        ps.setInt(3, salaryGrade.getMaxSalary());

        int nRowsAffected = ps.executeUpdate();
        int newId = 0;
        if (nRowsAffected > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Oops in add: " + e);
            }
            salaryGrade.setId(newId);
            return salaryGrade;
        }
        return null;
    }

    public List<SalaryGrade> getAll() throws SQLException {
        List<SalaryGrade> salaryGrades = new ArrayList<>();
        PreparedStatement ps = this.connection.prepareStatement(
                "SELECT * FROM salary_grades;");

        ResultSet results = ps.executeQuery();

        while (results.next()) {
            SalaryGrade salaryGrade = new SalaryGrade(
                    results.getInt("id"),
                    results.getString("grade"),
                    results.getInt("min_salary"),
                    results.getInt("max_salary"));
            salaryGrades.add(salaryGrade);
        }

        return salaryGrades;
    }

    public SalaryGrade getOne(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(
                "SELECT * FROM salary_grades WHERE id = ?;");
        ps.setInt(1, id);

        ResultSet results = ps.executeQuery();
        if (results.next()) {
            return new SalaryGrade(
                    results.getInt("id"),
                    results.getString("grade"),
                    results.getInt("min_salary"),
                    results.getInt("max_salary"));
        }
        return null;
    }

    public SalaryGrade updateOne(int id, SalaryGrade salaryGrade) throws SQLException {
        String sql = """
                UPDATE salary_grades
                SET grade = ?,
                min_salary = ?,
                max_salary = ?
                WHERE id = ?;
                """;

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setString(1, salaryGrade.getGrade());
        ps.setInt(2, salaryGrade.getMinSalary());
        ps.setInt(3, salaryGrade.getMaxSalary());
        ps.setInt(4, id);

        int nRowsAffected = ps.executeUpdate();
        if (nRowsAffected > 0) {
            return this.getOne(id);
        }
        return null;
    }

    public SalaryGrade deleteOne(int id) throws SQLException {
        String sql = """
                DELETE FROM salaryGrades
                WHERE id = ?;
                """;
        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setInt(1, id);

        SalaryGrade salaryGradeToDelete = this.getOne(id);

        int nRowsAffected = ps.executeUpdate();
        if (nRowsAffected > 0) {
            return salaryGradeToDelete;
        }
        return null;
    }
}
