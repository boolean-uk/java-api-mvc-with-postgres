package com.booleanuk.api.salaries;

import com.booleanuk.api.ConnectToDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalariesRepository {
    private ConnectToDatabase connectToDatabase;

    public SalariesRepository() throws SQLException {
        connectToDatabase = new ConnectToDatabase();
    }

    public List<Salaries> getAll() throws SQLException {
        List<Salaries> everyOne = new ArrayList<>();
        PreparedStatement statement = this.connectToDatabase.getConnection().
                prepareStatement("SELECT * FROM Salaries");
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            Salaries salaries = new Salaries(
                    results.getInt("id"),
                    results.getString("grade"),
                    results.getString("minSalary"),
                    results.getString("maxSalary"));
            everyOne.add(salaries);
        }
        return everyOne;
    }

    public Salaries getOne(int id) throws SQLException {
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement("SELECT * FROM Salaries WHERE id= ?");
        statement.setInt(1, id);

        ResultSet results = statement.executeQuery();
        Salaries salaries = null;
        if (results.next()) {
            salaries = new Salaries(
                    results.getInt("id"),
                    results.getString("grade"),
                    results.getString("minSalary"),
                    results.getString("maxSalary"));
            return salaries;
        }
        return salaries;
    }

    public Salaries update(int id, Salaries salaries) throws SQLException {
        String SQL = "UPDATE Salaries " +
                "SET name = ? ," +
                "location = ? ," +
                "WHERE id = ? ";
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(SQL);
        statement.setString(1, salaries.getGrade());
        statement.setString(2, salaries.getMinSalary());
        statement.setString(3, salaries.getMaxSalary());
        statement.setInt(4, id);
        int rowsAffected = statement.executeUpdate();
        Salaries updatedSalaries = null;
        if (rowsAffected > 0) {
            updatedSalaries = this.getOne(id);
        }
        return updatedSalaries;
    }

    public Salaries delete(int id) throws SQLException {
        String SQL = "DELETE FROM Salaries WHERE id=?";
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(SQL);
        statement.setInt(1, id);
        Salaries deletedSalaries = null;
        deletedSalaries = this.getOne(id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalaries = null;
        }
        return deletedSalaries;
    }

    public Salaries add(Salaries salaries) throws SQLException {
        String SQL = "INSERT INTO Salaries (name, location) VALUES (?,?,?)";
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salaries.getGrade());
        statement.setString(2, salaries.getMinSalary());
        statement.setString(3, salaries.getMaxSalary());
        int rowsAffected = statement.executeUpdate();
        int newID = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newID = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            salaries.setId(newID);
        } else {
            salaries = null;
        }
        return salaries;

    }
}
