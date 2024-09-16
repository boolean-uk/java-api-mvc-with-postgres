package com.booleanuk.api;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SalaryGradeRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String bdDatabase;
    private Connection connection;

    public SalaryGradeRepository() throws SQLException {
        getDatabaseCredentials();
        dataSource = createDataSource();
        connection = dataSource.getConnection();
    }

    public SalaryGrade add(SalaryGrade salaryGrade) throws SQLException{
        String SQL = "INSERT INTO salary_grade " +
                "(grade, min_salary, max_salary) " +
                "VALUES (?, ?, ?);";

        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, salaryGrade.getGrade());
        statement.setInt(2, salaryGrade.getMinSalary());
        statement.setInt(3, salaryGrade.getMaxSalary());
        statement.executeUpdate();

        return salaryGrade;
    }

    public List<SalaryGrade> getAll() throws SQLException{
        List<SalaryGrade> sgs = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM salary_grades;");
        ResultSet results = statement.executeQuery();

        while (results.next()){
            SalaryGrade theSalaryGrade = new SalaryGrade(
                    results.getString("grade"),
                    results.getInt("min_salary"),
                    results.getInt("max_salary")
            );
            sgs.add(theSalaryGrade);
        }

        return sgs;
    }

    public SalaryGrade getOne(String grade) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM salary_grades WHERE grade = ?;");
        statement.setString(1, grade);
        ResultSet results = statement.executeQuery();
        SalaryGrade salaryGrade = null;

        if (results.next()){
            salaryGrade = new SalaryGrade(
                    results.getString("grade"),
                    results.getInt("min_salary"),
                    results.getInt("max_salary")
            );
        }

        return salaryGrade;
    }

    public SalaryGrade update(String grade, SalaryGrade salaryGrade) throws SQLException{
        String sql = "UPDATE salary_grade " +
                "SET min_salary = ?, " +
                "max_salary = ?, " +
                "WHERE grade = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, salaryGrade.getMinSalary());
        statement.setInt(2, salaryGrade.getMaxSalary());
        statement.setString(3, salaryGrade.getGrade());
        statement.executeUpdate();

        return getOne(grade);
    }

    public SalaryGrade delete(String grade) throws SQLException{
        String sql = "DELETE FROM salary_grade WHERE grade = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        SalaryGrade toDelete = getOne(grade);

        statement.setString(1, grade);
        statement.executeUpdate();

        return toDelete;
    }

    public SalaryGrade findEmployeeFromGrade(String grade) throws SQLException{
        for (SalaryGrade salaryGrade : getAll()){
            if (salaryGrade.getGrade().equals(grade)){
                return salaryGrade;
            }
        }

        return null;
    }

    private void getDatabaseCredentials(){
        try {
            InputStream input = new FileInputStream("src/main/resources/config.properties");
            Properties prop = new Properties();

            prop.load(input);
            dbUser = prop.getProperty("db.user");
            dbURL = prop.getProperty("db.url");
            dbPassword = prop.getProperty("db.password");
            bdDatabase = prop.getProperty("db.database");

        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private DataSource createDataSource(){
        String url = "jdbc:postgresql://" + dbURL +
                ":5432/" + bdDatabase +
                "?user=" + dbUser +
                "&password=" + dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);

        return dataSource;
    }
}
