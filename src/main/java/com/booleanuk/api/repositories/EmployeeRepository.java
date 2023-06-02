package com.booleanuk.api.repositories;

import com.booleanuk.api.data.DbAccess;
import com.booleanuk.api.models.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

//    private DbAccess dbAcces;
//
//    public EmployeeRepository() throws SQLException {
//        this.dbAcces = new DbAccess();
//    }
//
//    public List<Employee> getAll() throws SQLException {
//        List<Employee> everyone = new ArrayList<>();
//        PreparedStatement statement = this.dbAcces.getAccess().prepareStatement("SELECT * FROM Employee");
//
//        ResultSet results = statement.executeQuery();
//
//        while (results.next()) {
//            Employee theEmployee = new Employee(
//                    results.getLong("id"),
//                    results.getString("name"),
//                    results.getString("address"),
//                    results.getString("email"),
//                    results.getString("phone"));
//            everyone.add(theCustomer);
//        }
//        return everyone;
//    }
//
//    public Customer get(long id) throws SQLException {
//        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Customers WHERE id = ?");
//        // Choose set**** matching the datatype of the missing element
//        statement.setLong(1, id);
//        ResultSet results = statement.executeQuery();
//        Customer customer = null;
//        if (results.next()) {
//            customer = new Customer(results.getLong("id"), results.getString("name"), results.getString("address"), results.getString("email"), results.getString("phone"));
//        }
//        return customer;
//    }
//
//    public Customer update(long id, Customer customer) throws SQLException {
//        String SQL = "UPDATE Customers " +
//                "SET name = ? ," +
//                "address = ? ," +
//                "email = ? ," +
//                "phone = ? " +
//                "WHERE id = ? ";
//        PreparedStatement statement = this.connection.prepareStatement(SQL);
//        statement.setString(1, customer.getName());
//        statement.setString(2, customer.getAddress());
//        statement.setString(3, customer.getEmail());
//        statement.setString(4, customer.getPhoneNumber());
//        statement.setLong(5, id);
//        int rowsAffected = statement.executeUpdate();
//        Customer updatedCustomer = null;
//        if (rowsAffected > 0) {
//            updatedCustomer = this.get(id);
//        }
//        return updatedCustomer;
//    }
//
//    public Customer delete(long id) throws SQLException {
//        String SQL = "DELETE FROM Customers WHERE id = ?";
//        PreparedStatement statement = this.connection.prepareStatement(SQL);
//        // Get the customer we're deleting before we delete them
//        Customer deletedCustomer = null;
//        deletedCustomer = this.get(id);
//
//        statement.setLong(1, id);
//        int rowsAffected = statement.executeUpdate();
//        if (rowsAffected == 0) {
//            //Reset the customer we're deleting if we didn't delete them
//            deletedCustomer = null;
//        }
//        return deletedCustomer;
//    }
//
//    public Customer add(Customer customer) throws SQLException {
//        String SQL = "INSERT INTO Customers(name, address, email, phone) VALUES(?, ?, ?, ?)";
//        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
//        statement.setString(1, customer.getName());
//        statement.setString(2, customer.getAddress());
//        statement.setString(3, customer.getEmail());
//        statement.setString(4, customer.getPhoneNumber());
//        int rowsAffected = statement.executeUpdate();
//        long newId = 0;
//        if (rowsAffected > 0) {
//            try (ResultSet rs = statement.getGeneratedKeys()) {
//                if (rs.next()) {
//                    newId = rs.getLong(1);
//                }
//            } catch (Exception e) {
//                System.out.println("Oops: " + e);
//            }
//            customer.setId(newId);
//        } else {
//            customer = null;
//        }
//        return customer;
//    }
}
