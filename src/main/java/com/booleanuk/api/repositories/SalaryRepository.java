package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Salary;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalaryRepository {
    //region // FIELDS //
    DataSource datasource;
    Connection connection;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    //endregion

    public SalaryRepository() throws SQLException  {
        this.getDatabaseCredentials();
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
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
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    // ---------------------------------------------- //
    // -------------------- CRUD -------------------- //
    // ---------------------------------------------- //

    //region // CREATE //
    public Salary add(Salary salary) throws SQLException {
        String SQL = "insert into salaries (grade, min_salary, max_salary) values (?, ?, ?)";
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
    //endregion

    //region // GET //
    public List<Salary> getAll() throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("select * from salaries");

        ResultSet results = statement.executeQuery();

        List<Salary> salaries = new ArrayList<>();
        while (results.next()) {
            Salary salary = new Salary(results.getLong("id"), results.getString("grade"), results.getInt("min_salary"), results.getInt("max_salary"));
            salaries.add(salary);
        }
        return salaries;
    }

    public Salary get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("select * from salaries where id = ?");
        statement.setLong(1, id);

        ResultSet results = statement.executeQuery();

        Salary salary = null;
        if (results.next()) {
            salary = new Salary(results.getLong("id"), results.getString("grade"), results.getInt("min_salary"), results.getInt("max_salary"));
        }
        return salary;
    }
    //endregion

    //region // UPDATE //
    public Salary update(long id, Salary salary) throws SQLException {
        String SQL = "update salaries " +
                "set grade = ? ," +
                "min_salary = ? ," +
                "max_salary = ? ," +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        statement.setLong(4, id);

        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.get(id);
        }
        return updatedSalary;
    }
    //endregion

    //region // DELETE //
    public Salary delete(long id) throws SQLException {
        String SQL = "delete from salaries where id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setLong(1, id);

        Salary deletedSalary = null;
        deletedSalary = this.get(id);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }
    //endregion
}
