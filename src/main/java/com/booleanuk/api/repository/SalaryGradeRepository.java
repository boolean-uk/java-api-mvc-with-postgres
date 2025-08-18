package com.booleanuk.api.repository;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.model.SalaryGrade;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalaryGradeRepository {




    public DataSource dataSource;
    private Connection connection;

    private String dbUser;
    private String dbUrl;
    private String dbPassword;
    private String dbDatabase;

    public SalaryGradeRepository() throws SQLException {
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



    public List<SalaryGrade> getAll() throws SQLException  {
        List<SalaryGrade> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaryGrades");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            SalaryGrade employee = new SalaryGrade(
                    results.getInt("id"),
                    results.getString("grade"),
                    results.getInt("minSalary"),
                    results.getInt("maxSalary")
            );

            everyone.add(employee);
        }
        return everyone;
    }


    public SalaryGrade getSalaryGrade(int id)throws SQLException {

        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaryGrades WHERE ID = ?");
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? new SalaryGrade(resultSet.getInt("id"),
                resultSet.getString("grade"),
                resultSet.getInt("minSalary"),
                resultSet.getInt("maxSalary")) : null;
    }

    public SalaryGrade add(SalaryGrade salaryGrade) throws SQLException {
        String SQL = "INSERT INTO salaryGrades(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salaryGrade.getGrade());
        statement.setInt(2, salaryGrade.getMinSalary());
        statement.setInt(3, salaryGrade.getMaxSalary());
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



    public SalaryGrade update(int id, SalaryGrade salaryGrade) throws SQLException {
        String SQL = "UPDATE salaryGrades " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salaryGrade.getGrade());
        statement.setInt(2, salaryGrade.getMinSalary());
        statement.setInt(3, salaryGrade.getMaxSalary());
        statement.setLong(4, id);
        int rowsAffected = statement.executeUpdate();
        SalaryGrade updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.getSalaryGrade(id);
        }
        return updatedEmployee;
    }




    public SalaryGrade delete(int id) throws SQLException {
        String SQL = "DELETE FROM salaryGrade WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the Employee we're deleting before we delete them
        SalaryGrade deletedSalaryGrade = null;
        deletedSalaryGrade = this.getSalaryGrade(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the employee we're deleting if we didn't delete them
            deletedSalaryGrade = null;
        }
        return deletedSalaryGrade;
    }





}
