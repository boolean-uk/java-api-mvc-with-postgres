package com.booleanuk.api.repositories;

import com.booleanuk.api.data.DbAccess;
import com.booleanuk.api.models.Department;
import com.booleanuk.api.models.Employee;
import com.booleanuk.api.models.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class EmployeeRepository {

    @Autowired
    private DbAccess dbAccess;
    @Autowired
    private SalaryRepository salaryRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    public EmployeeRepository(DbAccess dbAccess) throws SQLException {
        this.dbAccess = dbAccess;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(
                "SELECT e.id, e.name, e.jobName, s.grade as salaryGrade, d.name as department " +
                        "FROM Employee e " +
                        "JOIN salary s ON e.salary_id=s.id " +
                        "JOIN department d ON e.department_id=d.id ");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee theEmployee = new Employee(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee get(long id) throws SQLException {
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(
                "SELECT e.id, e.name, e.jobName, s.grade as salaryGrade, d.name as department " +
                        "FROM Employee e " +
                        "JOIN salary s ON e.salary_id=s.id " +
                        "JOIN department d ON e.department_id=d.id " +
                        "WHERE e.id = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()) {
            employee = new Employee(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department"));
        }
        return employee;
    }
//
    public Employee update(long id, Employee employee) throws SQLException {

        Salary salary = salaryRepository.getByGrade(employee.getSalaryGrade());
        Department department = departmentRepository.getByName(employee.getDepartment());

        String SQL = "UPDATE Employee " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salary_id = ? ," +
                "department_id = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
//        statement.setString(3, employee.getSalaryGrade());
//        statement.setString(4, employee.getDepartment());
        statement.setLong(3, salary.getId());
        statement.setLong(4, department.getId());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }


    public Employee delete(long id) throws SQLException {
        String SQL = "DELETE FROM Employee WHERE id = ?";
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(SQL);
        // Get the employee we're deleting before we delete them
        Employee deletedEmployee = null;
        deletedEmployee = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the employee we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) throws SQLException {
        Salary salary = salaryRepository.getByGrade(employee.getSalaryGrade());
        Department department = departmentRepository.getByName(employee.getDepartment());

        String SQL = "INSERT INTO Employee(name, jobName, salary_id,department_id)" +
                     "VALUES (?, ?, ?,?)";
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setLong(3, salary.getId());
        statement.setLong(4, department.getId());

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
            employee.setId(newId);
        } else {
            employee = null;
        }
        return employee;
    }
}
