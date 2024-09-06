package com.booleanuk.api.departments;

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

public class DepartmentRepository implements Repository<Department, DepartmentDTO> {
  private final Connection connection;

  public DepartmentRepository(Connection connection) {
    this.connection = connection;
  }

  private static List<Department> sqlResultToSalaries(ResultSet result) throws SQLException {
    List<Department> salaries = new ArrayList<>();
    while (result.next()) {
      salaries.add(new Department(
          result.getInt("id"),
          result.getString("name"),
          result.getString("location")));
    }

    return salaries.stream()
        .sorted((department1, department2) -> department1.id() - department2.id())
        .toList();
  }

  public List<Department> get() throws ResponseStatusException {
    try {
      PreparedStatement selection = this.connection.prepareStatement("SELECT * FROM departments;");
      return DepartmentRepository.sqlResultToSalaries(selection.executeQuery());
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  public Department get(int id) throws ResponseStatusException {
    try {
      PreparedStatement selection = this.connection
          .prepareStatement("SELECT * FROM departments WHERE id = ?");
      selection.setInt(1, id);
      List<Department> salaries = sqlResultToSalaries(selection.executeQuery());
      if (salaries.isEmpty())
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      else
        return salaries.getFirst();
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  public Department post(DepartmentDTO department) throws ResponseStatusException {
    try {
      PreparedStatement insertion = this.connection
          .prepareStatement("INSERT INTO departments(name, location) VALUES(?, ?);",
              Statement.RETURN_GENERATED_KEYS);
      insertion.setString(1, department.name());
      insertion.setString(2, department.location());
      insertion.executeUpdate();

      ResultSet resultSet = insertion.getGeneratedKeys();
      resultSet.next();
      int createdId = resultSet.getInt("id");

      return new Department(createdId, department.name(), department.location());
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  public Department put(int id, DepartmentDTO department) throws ResponseStatusException {
    try {
      PreparedStatement update = this.connection
          .prepareStatement(
              "UPDATE departments SET name = ?, location = ? WHERE id = ?");
      update.setString(1, department.name());
      update.setString(2, department.location());
      update.setInt(3, id);
      int updatedRows = update.executeUpdate();
      if (updatedRows == 0)
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

      return new Department(id, department.name(), department.location());
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  public Department delete(int id) throws ResponseStatusException {
    try {
      PreparedStatement deletion = this.connection.prepareStatement("DELETE FROM departments WHERE id = ?",
          Statement.RETURN_GENERATED_KEYS);
      deletion.setInt(1, id);
      int updatedRows = deletion.executeUpdate();
      if (updatedRows == 0)
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

      ResultSet resultSet = deletion.getGeneratedKeys();
      resultSet.next();
      String deletedName = resultSet.getString("name");
      String deletedLocation = resultSet.getString("location");

      return new Department(id, deletedName, deletedLocation);
    } catch (SQLException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }
}
