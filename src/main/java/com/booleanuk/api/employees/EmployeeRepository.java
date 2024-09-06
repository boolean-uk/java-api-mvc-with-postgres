package com.booleanuk.api.employees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.booleanuk.api.base.Repository;

public class EmployeeRepository implements Repository<Employee, EmployeeDTO> {
  private final Connection connection;

  public EmployeeRepository(Connection connection) {
    this.connection = connection;
  }

  private static List<Employee> sqlResultToEmployees(ResultSet result) throws SQLException {
    List<Employee> employees = new ArrayList<>();
    while (result.next()) {
      employees.add(new Employee(
          result.getInt("id"),
          result.getString("name"),
          result.getString("job_name"),
          result.getString("salary_grade"),
          result.getString("department")));
    }

    return employees.stream()
        .sorted((employee1, employee2) -> employee1.id() - employee2.id())
        .toList();
  }

  public List<Employee> get() throws ResponseStatusException {
    try {
      PreparedStatement selection = this.connection.prepareStatement("SELECT * FROM employees;");
      return EmployeeRepository.sqlResultToEmployees(selection.executeQuery());
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  public Employee get(int id) throws ResponseStatusException {
    try {
      PreparedStatement selection = this.connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
      selection.setInt(1, id);
      List<Employee> employees = sqlResultToEmployees(selection.executeQuery());
      if (employees.isEmpty())
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      else
        return employees.getFirst();
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  public Employee post(EmployeeDTO employeeDTO) throws ResponseStatusException {
    try {
      PreparedStatement insertion = this.connection
          .prepareStatement(
              "INSERT INTO employees(name, job_name, salary_grade, department) VALUES(?, ?, ?, ?);",
              Statement.RETURN_GENERATED_KEYS);
      insertion.setString(1, employeeDTO.name());
      insertion.setString(2, employeeDTO.jobName());
      insertion.setString(3, employeeDTO.salaryGrade());
      insertion.setString(4, employeeDTO.department());
      insertion.executeUpdate();

      ResultSet resultSet = insertion.getGeneratedKeys();
      resultSet.next();
      int createdId = resultSet.getInt("id");

      return new Employee(createdId, employeeDTO.name(), employeeDTO.jobName(), employeeDTO.salaryGrade(),
          employeeDTO.department());
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  public Employee put(int id, EmployeeDTO employeeDTO) throws ResponseStatusException {
    try {
      PreparedStatement update = this.connection
          .prepareStatement(
              "UPDATE employees SET name = ?, job_name = ?, salary_grade = ?, department = ? WHERE id = ?");
      update.setString(1, employeeDTO.name());
      update.setString(2, employeeDTO.jobName());
      update.setString(3, employeeDTO.salaryGrade());
      update.setString(4, employeeDTO.department());
      update.setInt(5, id);
      int updatedRows = update.executeUpdate();
      if (updatedRows == 0)
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

      return new Employee(id, employeeDTO.name(), employeeDTO.jobName(), employeeDTO.salaryGrade(),
          employeeDTO.department());
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  public Employee delete(int id) throws ResponseStatusException {
    try {
      PreparedStatement deletion = this.connection.prepareStatement("DELETE FROM employees WHERE id = ?",
          Statement.RETURN_GENERATED_KEYS);
      deletion.setInt(1, id);
      int updatedRows = deletion.executeUpdate();
      if (updatedRows == 0)
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

      ResultSet resultSet = deletion.getGeneratedKeys();
      resultSet.next();
      String deletedName = resultSet.getString("name");
      String deletedJobName = resultSet.getString("job_name");
      String deletedSalaryGrade = resultSet.getString("salary_grade");
      String deletedDepartment = resultSet.getString("department");

      return new Employee(id, deletedName, deletedJobName, deletedSalaryGrade, deletedDepartment);
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }
}
