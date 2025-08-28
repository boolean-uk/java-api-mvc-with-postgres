package com.booleanuk.api.repository;


import com.booleanuk.api.model.Employee;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class EmployeeRepository {
    private static String url;
    private static String user;
    private static String pws;

    private Connection connection;

    static {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            pws = prop.getProperty("db.pws");
        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }
    }
    public EmployeeRepository() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(url+"?user="+user+"&password="+pws);
        connection = ds.getConnection();
    }
    public Employee createEmployee(Employee employee) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Employees (name, jobName, salaryGrade, department) "
                + " VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getJobName());
        ps.setString(3, employee.getSalaryGrade());
        ps.setString(4, employee.getDepartment());

        int affRowCount = ps.executeUpdate();
        if(affRowCount > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return getSpecificEmployee(rs.getInt(1));
                }
            }
        }
        return null;
    }
    public List<Employee> getEmployees() throws SQLException{
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Employees"
        );
        List<Employee> employees = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employee e = new Employee(rs);
            employees.add(e);

        }
        return employees;
    }

    public Employee getSpecificEmployee(int id) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Employees WHERE id = ?");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Employee(rs);
        }
        return null;
    }
    public Employee updateEmployee(Employee employee) throws  SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE Employees SET"
                + " name=?, "
                + " jobName=?, "
                + " salaryGrade=?, "
                + " department=? "
                + " WHERE id=?"
        );
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getJobName());
        ps.setString(3, employee.getSalaryGrade());
        ps.setString(4, employee.getDepartment());
        ps.setInt(5, employee.getId());

        int affRowCount = ps.executeUpdate();
        if (affRowCount > 0) {
            return getSpecificEmployee(employee.getId());
        }
        return null;
    }

    public Employee deleteEmployee(int id) throws SQLException {
        Employee e = getSpecificEmployee(id);
        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM Employees WHERE id=?"
        );
        ps.setInt(1, id);

        int affRowCount = ps.executeUpdate();
        if (affRowCount > 0) {
            return e;
        }
        return null;
    }
}
