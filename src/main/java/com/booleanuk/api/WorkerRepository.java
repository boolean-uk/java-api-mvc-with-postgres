package com.booleanuk.api;

import javax.sql.DataSource;

import com.booleanuk.api.Worker;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WorkerRepository {
    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public WorkerRepository() throws SQLException{
        this.connection=this.datasource.getConnection();
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

    private DataSource createDataSource(){
        final String url = "jdbc:postgresql://" + this.dbURL
                + ":5432/" + this.dbDatabase
                + "?user=" + this.dbUser
                + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource=new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }

    public List<Worker> getAll() throws SQLException  {
        List<Worker> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Workers");
        ResultSet results =statement.executeQuery();

        while(results.next()){
            Worker theWorker = new Worker(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
            everyone.add(theWorker);
        }
        return everyone;
    }

    public Worker get(long id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Workers WHERE id = ?");
        statement.setLong(1,id);
        ResultSet results= statement.executeQuery();
        Worker worker=null;
        if (results.next()) {
            worker = new Worker(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
        }
        return worker;
    }

    public Worker update(long id, Worker worker) throws SQLException {
        String SQL = "UPDATE Workers " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade = ? ," +
                "department = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, worker.getName());
        statement.setString(2, worker.getJobName());
        statement.setString(3, worker.getSalaryGrade());
        statement.setString(4, worker.getDepartment());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Worker updatedWorker = null;
        if (rowsAffected>0){
            updatedWorker=this.get(id);
        }
        return updatedWorker;
    }

    public Worker delete(long id) throws SQLException {
        String SQL = "DELETE FROM Workers WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Worker deletedWorker = null;
        deletedWorker = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedWorker = null;
        }
        return deletedWorker;
    }

    public Worker add(Worker worker) throws SQLException {
        String SQL = "INSERT INTO Workers(name, jobName, salaryGrade, department) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, worker.getName());
        statement.setString(2, worker.getJobName());
        statement.setString(3, worker.getSalaryGrade());
        statement.setString(4, worker.getDepartment());
        int rowsAffected = statement.executeUpdate();
        long newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getLong(1);
                }
            } catch (Exception e) {
                System.out.println("Uh oh. Stinky: " + e);
            }
            worker.setId(newId);
        } else {
            worker = null;
        }
        return worker;
    }
}







