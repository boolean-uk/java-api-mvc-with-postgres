package com.booleanuk.api.department;

import com.booleanuk.api.employee.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    private DepartmentRepository departments;

    public DepartmentController() throws SQLException {
        departments = new DepartmentRepository();
    }

    @GetMapping
    public List<Department> getAllDepartments() throws SQLException {
        return this.departments.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getOneDepartment(@PathVariable(name = "id") int id) throws SQLException{
        if (this.departments.getOneDepartment(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return this.departments.getOneDepartment(id);
        }
    }
}
