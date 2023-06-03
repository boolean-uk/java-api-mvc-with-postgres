package com.booleanuk.api.salaries;
import com.booleanuk.api.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalaryRepository {
    private MyConnection connection;

    public SalaryRepository() throws SQLException{
        this.connection = new MyConnection();
    }
    public List<Salary> getAll() throws SQLException{
        List<Salary> all = new ArrayList<>();
        PreparedStatement statement = this.connection.getConnection().prepareStatement("SELECT * FROM Salaries");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            all.add(new Salary(result.getInt("id"),result.getInt("min_salary"),result.getInt("max_salary"), result.getString("grade")));
        }
        return all;
    }
    public Salary getSalary(int id)throws SQLException{
        PreparedStatement statement = this.connection.getConnection().prepareStatement("SELECT * FROM Salaries WHERE id=?");
        statement.setInt(1,id);
        ResultSet result = statement.executeQuery();
        Salary temp = null;
        if(result.next()){
            temp = new Salary(result.getInt("id"),result.getInt("min_salary"),result.getInt("max_salary"), result.getString("grade"));
        }
        return temp;
    }
    public Salary updateSalary(int id, Salary salary) throws SQLException {
        String SQl = "UPDATE Salaries SET min_salary = ? , max_salary = ?, grade = ? WHERE id = ?";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(SQl);
        statement.setInt(1,salary.getMinSalary());
        statement.setInt(2,salary.getMaxSalary());
        statement.setString(3,salary.getGrade());
        statement.setInt(4,id);
        if(salary.getMaxSalary() == 0 || salary.getMinSalary() == 0|| salary.getGrade() == null){
            return new Salary(-1,0,0,"N/A");
        }
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if(rowsAffected>0){
            updatedSalary = this.getSalary(id);
        }
        return updatedSalary;
    }
    public Salary deleteSalary(int id) throws SQLException{
        PreparedStatement statement = this.connection.getConnection().prepareStatement("DELETE FROM Salaries WHERE id =?");
        Salary deletedSalary = this.getSalary(id);
        statement.setInt(1,id);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected==0){
            deletedSalary = null;
        }
        return deletedSalary;

    }
    public Salary addSalary(Salary salary) throws SQLException {
        String SQL = "INSERT INTO Salaries(min_salary,max_salary, grade) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, salary.getMinSalary());
        statement.setInt(2, salary.getMaxSalary());
        statement.setString(3,salary.getGrade());
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
            salary.setId(newId);
        } else {
            salary = null;
        }
        return salary;
    }
}
