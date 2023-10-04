package com.booleanuk.api.employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.booleanuk.api.dbConfiguration.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    private final Connection connection;
    @Autowired
    public EmployeeRepository(DatabaseConnection dbConnection){
        this.connection = dbConnection.getConnection();
    }


// LEFT THIS CODE HERE ON PURPOSE - THESE ARE THE METHODS THAT WORK FOR THE CORE PART OF THE EXERCISE

//    public List<Employee> getAll() throws SQLException  {
//        List<Employee> everyone = new ArrayList<>();
//        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees");
//
//        ResultSet results = statement.executeQuery();
//
//        while (results.next()) {
//            Employee theEmployee = new Employee(results.getLong("id"),
//                    results.getString("name"), results.getString("jobName"),
//                    results.getString("salaryGrade"), results.getString("department"));
//            everyone.add(theEmployee);
//        }
//        return everyone;
//    }
//
//    public Employee get(long id) throws SQLException {
//        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");
//        statement.setLong(1, id);
//        ResultSet results = statement.executeQuery();
//        Employee employee = null;
//        if (results.next()) {
//            employee = new Employee(results.getLong("id"),
//                    results.getString("name"), results.getString("jobName"),
//                    results.getString("salaryGrade"), results.getString("department"));
//        }
//        return employee;
//    }
//
//    public Employee update(long id, Employee employee) throws SQLException {
//        String SQL = "UPDATE Employees " +
//                "SET name = ? ," +
//                "jobName = ? ," +
//                "salaryGrade = ? ," +
//                "department = ? " +
//                "WHERE id = ? ";
//        PreparedStatement statement = this.connection.prepareStatement(SQL);
//        statement.setString(1, employee.getName());
//        statement.setString(2, employee.getJobName());
//        statement.setString(3, employee.getSalaryGrade());
//        statement.setString(4, employee.getDepartment());
//        statement.setLong(5, id);
//        int rowsAffected = statement.executeUpdate();
//        Employee updatedEmployee = null;
//        if (rowsAffected > 0) {
//            updatedEmployee = this.get(id);
//        }
//        return updatedEmployee;
//    }
//
//    public Employee delete(long id) throws SQLException {
//        String SQL = "DELETE FROM Employees WHERE id = ?";
//        PreparedStatement statement = this.connection.prepareStatement(SQL);
//        Employee deletedEmployee = null;
//        deletedEmployee = this.get(id);
//
//        statement.setLong(1, id);
//        int rowsAffected = statement.executeUpdate();
//        if (rowsAffected == 0) {
//            deletedEmployee = null;
//        }
//        return deletedEmployee;
//    }
//
//    public Employee add(Employee employee) throws SQLException {
//        String SQL = "INSERT INTO Employees(name, jobName, salaryGrade, department) VALUES(?, ?, ?, ?)";
//        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
//        statement.setString(1, employee.getName());
//        statement.setString(2, employee.getJobName());
//        statement.setString(3, employee.getSalaryGrade());
//        statement.setString(4, employee.getDepartment());
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
//            employee.setId(newId);
//        } else {
//            employee = null;
//        }
//        return employee;
//    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT e.id, e.name, e.jobName, s.grade AS salaryGrade, d.name AS department " +
                "FROM Employees e " +
                "LEFT JOIN salaries s ON e.salary_grade_id = s.id " +
                "LEFT JOIN departments d ON e.department_id = d.id");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee theEmployee = new Employee(results.getLong("id"),
                    results.getString("name"), results.getString("jobName"),
                    results.getString("salaryGrade"), results.getString("department"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT e.id, e.name, e.jobName, s.grade AS salaryGrade, d.name AS department " +
                "FROM Employees e " +
                "LEFT JOIN salaries s ON e.salary_grade_id = s.id " +
                "LEFT JOIN departments d ON e.department_id = d.id " +
                "WHERE e.id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()) {
            employee = new Employee(results.getLong("id"),
                    results.getString("name"), results.getString("jobName"),
                    results.getString("salaryGrade"), results.getString("department"));
        }
        return employee;
    }
    public Employee update(long id, Employee employee) throws SQLException {
        Long departmentId = getDepartmentIdByName(employee.getDepartment());
        Long salaryGradeId = getSalaryGradeIdByGrade(employee.getSalaryGrade());

        String SQL = "UPDATE Employees " +
                "SET name = ? ," +
                "jobName = ? ," +
                "department_id = ?, " +
                "salary_grade_id = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setLong(3, departmentId);
        statement.setLong(4, salaryGradeId);
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }
    public Employee delete(long id) throws SQLException {
        Employee employeeToDelete = this.get(id);

        if (employeeToDelete != null) {
            String deleteEmployeeSQL = "DELETE FROM Employees WHERE id = ?";
            PreparedStatement deleteEmployeeStatement = this.connection.prepareStatement(deleteEmployeeSQL);
            deleteEmployeeStatement.setLong(1, id);
            int rowsAffected = deleteEmployeeStatement.executeUpdate();

            if (rowsAffected > 0) {
                return employeeToDelete;
            }
        }
        return null;
    }

    public Employee add(Employee employee) throws SQLException {
        String insertEmployeeSQL = "INSERT INTO Employees(name, jobName, salary_grade_id, department_id) VALUES(?, ?, ?, ?)";
        PreparedStatement insertEmployeeStatement = this.connection.prepareStatement(insertEmployeeSQL, Statement.RETURN_GENERATED_KEYS);
        insertEmployeeStatement.setString(1, employee.getName());
        insertEmployeeStatement.setString(2, employee.getJobName());

        Long salaryGradeId = getSalaryGradeIdByGrade(employee.getSalaryGrade());
        if (salaryGradeId != null) {
            insertEmployeeStatement.setLong(3, salaryGradeId);
        } else {
            salaryGradeId = createSalaryGradeAndGetId(employee.getSalaryGrade());
            if (salaryGradeId != null) {
                insertEmployeeStatement.setLong(3, salaryGradeId);
            } else {
                return null;
            }
        }

        Long departmentId = getDepartmentIdByName(employee.getDepartment());
        if (departmentId != null) {
            insertEmployeeStatement.setLong(4, departmentId);
        } else {
            departmentId = createDepartmentAndGetId(employee.getDepartment());
            if (departmentId != null) {
                insertEmployeeStatement.setLong(4, departmentId);
            } else {
                return null;
            }
        }

        int rowsAffected = insertEmployeeStatement.executeUpdate();
        long newId = 0;

        if (rowsAffected > 0) {
            try (ResultSet rs = insertEmployeeStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getLong(1);
                }
            } catch (Exception e) {
                System.out.println("Oops: " + e);
            }
            employee.setId(newId);
            return employee;
        } else {
            return null;
        }
    }
    public Long getDepartmentIdByName(String departmentName) throws SQLException {
        String SQL = "SELECT id FROM departments WHERE name = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, departmentName);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            return results.getLong("id");
        }
        return null;
    }

    public Long getSalaryGradeIdByGrade(String salaryGrade) throws SQLException {
        String SQL = "SELECT id FROM salaries WHERE grade = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salaryGrade);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            return results.getLong("id");
        }
        return null;
    }
    private Long createSalaryGradeAndGetId(String grade) throws SQLException {
        String createSalaryGradeSQL = "INSERT INTO salaries(grade) VALUES(?) RETURNING id";
        PreparedStatement createSalaryGradeStatement = this.connection.prepareStatement(createSalaryGradeSQL);
        createSalaryGradeStatement.setString(1, grade);

        ResultSet result = createSalaryGradeStatement.executeQuery();
        if (result.next()) {
            return result.getLong("id");
        }
        return null;
    }

    private Long createDepartmentAndGetId(String name) throws SQLException {
        String createDepartmentSQL = "INSERT INTO departments(name) VALUES(?) RETURNING id";
        PreparedStatement createDepartmentStatement = this.connection.prepareStatement(createDepartmentSQL);
        createDepartmentStatement.setString(1, name);

        ResultSet result = createDepartmentStatement.executeQuery();
        if (result.next()) {
            return result.getLong("id");
        }
        return null;
    }
}
