package com.booleanuk.api.departments;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booleanuk.api.base.Controller;
import com.booleanuk.api.database.DatabaseConnector;

@RestController
@RequestMapping(value = "/departments")
public class DepartmentController extends Controller<Department, DepartmentDTO> {
  public DepartmentController() throws SQLException {
    super(new DepartmentRepository(DatabaseConnector.getConnection()));
  }
}
