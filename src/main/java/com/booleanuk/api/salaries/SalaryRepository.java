package com.booleanuk.api.salaries;

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

public class SalaryRepository implements Repository<Salary, Salary> {
  private final Connection connection;

  public SalaryRepository(Connection connection) {
    this.connection = connection;
  }

  private static List<Salary> sqlResultToSalaries(ResultSet result) throws SQLException {
    List<Salary> salaries = new ArrayList<>();
    while (result.next()) {
      salaries.add(new Salary(
          result.getString("grade"),
          result.getInt("min_salary"),
          result.getInt("max_salary")));
    }

    return salaries.stream()
        .sorted((salary1, salary2) -> Integer.parseInt(salary1.grade()) - Integer.parseInt(salary2.grade()))
        .toList();
  }

  public List<Salary> get() throws ResponseStatusException {
    try {
      PreparedStatement selection = this.connection.prepareStatement("SELECT * FROM salaries;");
      return SalaryRepository.sqlResultToSalaries(selection.executeQuery());
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  public Salary get(int id) throws ResponseStatusException {
    try {
      PreparedStatement selection = this.connection
          .prepareStatement("SELECT * FROM salaries WHERE grade = ?");
      selection.setString(1, String.valueOf(id));
      List<Salary> salaries = sqlResultToSalaries(selection.executeQuery());
      if (salaries.isEmpty())
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      else
        return salaries.getFirst();
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  public Salary post(Salary salary) throws ResponseStatusException {
    try {
      PreparedStatement insertion = this.connection
          .prepareStatement("INSERT INTO salaries(grade, min_salary, max_salary) VALUES(?, ?, ?);",
              Statement.RETURN_GENERATED_KEYS);
      insertion.setString(1, salary.grade());
      insertion.setInt(2, salary.minSalary());
      insertion.setInt(3, salary.maxSalary());
      insertion.executeUpdate();

      return salary;
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  public Salary put(int id, Salary salary) throws ResponseStatusException {
    try {
      PreparedStatement update = this.connection
          .prepareStatement("UPDATE salaries SET min_salary = ?, max_salary = ? WHERE grade = ?");
      update.setInt(1, salary.minSalary());
      update.setInt(2, salary.maxSalary());
      update.setString(3, String.valueOf(id));
      int updatedRows = update.executeUpdate();
      if (updatedRows == 0)
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

      return salary;
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  public Salary delete(int id) throws ResponseStatusException {
    try {
      PreparedStatement deletion = this.connection.prepareStatement("DELETE FROM salaries WHERE grade = ?",
          Statement.RETURN_GENERATED_KEYS);
      deletion.setString(1, String.valueOf(id));
      int updatedRows = deletion.executeUpdate();
      if (updatedRows == 0)
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

      ResultSet resultSet = deletion.getGeneratedKeys();
      resultSet.next();
      String deletedGrade = resultSet.getString("grade");
      int deletedMinSalary = resultSet.getInt("min_salary");
      int deletedMaxSalary = resultSet.getInt("max_salary");

      return new Salary(deletedGrade, deletedMinSalary, deletedMaxSalary);
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }
}
