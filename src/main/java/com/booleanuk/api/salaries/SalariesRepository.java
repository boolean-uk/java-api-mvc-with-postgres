package com.booleanuk.api.salaries;

import com.booleanuk.api.employee.Employee;
import com.booleanuk.api.server.ServerConnection;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class SalariesRepository {
    private Connection connection = ServerConnection.getInstance();

    public SalariesRepository() throws SQLException {
    }

    public void connectToDatabase() throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String id = "" + result.getLong("id");
            String min_value = "" + result.getInt("min_salary");
            String max_value = "" + result.getInt("max_salary");
            System.out.println(id + " - " + min_value + " - " + max_value);
        }
    }

    public ArrayList<Salary> getAll() throws SQLException {
        ArrayList<Salary> salaries = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Salary salary = new Salary(
                    result.getLong("id"),
                    result.getInt("min_salary"),
                    result.getInt("max_salary")
            );
            salaries.add(salary);
        }

        return salaries;
    }

    public Salary getAll(long id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM SALARIES");
        ResultSet results = statement.executeQuery();
        Salary salary = null;

        if(results.next()) {
            salary = new Salary(results.getLong("id"), results.getInt("min_salary"), results.getInt("max_value"));
        }
        return salary;
    }

    public Salary get(long id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM SALARIES WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;

        if(results.next()) {
            salary = new Salary(results.getLong("id"), results.getInt("min_salary"), results.getInt("max_value"));
        }
        return salary;
    }

    public Salary update(long id, Salary salary) throws SQLException {
        String SQL = "UPDATE SALARIES " +
                "SET min_salary = ? ," +
                "max_salary = ?" +
                "WHERE id = ?";

        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, salary.getMin_salary());
        statement.setInt(2, salary.getMax_salary());
        statement.setLong(3, salary.getId());

        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if(rowsAffected > 0) {
            updatedSalary = this.get(id);
        }

        return updatedSalary;
    }

    public Salary delete(long id) throws SQLException {
        String SQL = "DELETE FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        Salary deletedSalary = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO salaries(min_salary, max_salary) VALUES (?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, salary.getMin_salary());
        statement.setInt(2, salary.getMax_salary());
        int rowsAffected = statement.executeUpdate();
        long newId = 0;
        if(rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if(rs.next()){
                    newId = rs.getLong(1);
                }
            }catch (Exception e) {
                System.out.println("Oops: " + e);
            }
            salary.setId(newId);
        }else {
            salary = null;
        }
        return salary;
    }
}
