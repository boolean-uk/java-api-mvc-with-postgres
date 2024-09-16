package com.booleanuk.api.employee;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.postgresql.ds.PGSimpleDataSource;

public class EmployeeRepository {
	DataSource datasource;
	String dbUser;
	String dbURL;
	String dbPassword;
	String dbDatabase;
	Connection connection;

	public EmployeeRepository() throws SQLException {
		this.getDatabaseCredentials();
		this.datasource = this.createDataSource();
		this.connection = this.datasource.getConnection();
	}

	private void getDatabaseCredentials() {
		try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
			Properties prop = new Properties();
			prop.load(input);
			this.dbUser = prop.getProperty("db.user");
			this.dbURL = prop.getProperty("db.url");
			this.dbPassword = prop.getProperty("db.password");
			this.dbDatabase = prop.getProperty("db.database");
		} catch (Exception e) {
			System.out.println("Oops: " + e);
		}
	}

	private DataSource createDataSource() {

		final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
		final PGSimpleDataSource dataSource = new PGSimpleDataSource();
		dataSource.setUrl(url);
		return dataSource;
	}

	public List<Employee> getAll() throws SQLException {
		List<Employee> everyone = new ArrayList<>();
		PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees");
		ResultSet results = statement.executeQuery();
		while (results.next()) {
			String salaryGradeSQL = "SELECT grade FROM Salary_grades WHERE id = ?";
			PreparedStatement salaryGradeStatement = this.connection.prepareStatement(salaryGradeSQL);
			salaryGradeStatement.setLong(1, results.getLong("salary_grade"));
			ResultSet salaryGradeResult = salaryGradeStatement.executeQuery();
			String salaryGrade = "";
			if (salaryGradeResult.next()) {
				salaryGrade = salaryGradeResult.getString("grade");
			}

			String departmentSQL = "SELECT name FROM Departments WHERE id = ?";
			PreparedStatement departmentStatement = this.connection.prepareStatement(departmentSQL);
			departmentStatement.setLong(1, results.getLong("department"));
			ResultSet departmentResult = departmentStatement.executeQuery();
			String department = "";
			if (departmentResult.next()) {
				department = departmentResult.getString("name");
			}
			Employee employee = new Employee(results.getLong("id"), results.getString("name"), results.getString("job_name"), salaryGrade, department);
			everyone.add(employee);
		}
		return everyone;
	}

	public Employee get(long id) throws SQLException {
		PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");
		statement.setLong(1, id);
		ResultSet results = statement.executeQuery();
		Employee employee = null;
		if (results.next()) {
			String salaryGradeSQL = "SELECT grade FROM Salary_grades WHERE id = ?";
			PreparedStatement salaryGradeStatement = this.connection.prepareStatement(salaryGradeSQL);
			salaryGradeStatement.setLong(1, results.getLong("salary_grade"));
			ResultSet salaryGradeResult = salaryGradeStatement.executeQuery();
			String salaryGrade = "";
			if (salaryGradeResult.next()) {
				salaryGrade = salaryGradeResult.getString("grade");
			}

			String departmentSQL = "SELECT name FROM Departments WHERE id = ?";
			PreparedStatement departmentStatement = this.connection.prepareStatement(departmentSQL);
			departmentStatement.setLong(1, results.getLong("department"));
			ResultSet departmentResult = departmentStatement.executeQuery();
			String department = "";
			if (departmentResult.next()) {
				department = departmentResult.getString("name");
			}
			employee = new Employee(results.getLong("id"), results.getString("name"), results.getString("job_name"), salaryGrade, department);
		}
		return employee;
	}

	public Employee add(Employee employee) throws SQLException {
		String SQL = "INSERT INTO Employees (name, job_name, salary_grade, department) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, employee.getName());
		statement.setString(2, employee.getJobName());

		String salaryGradeSQL = "SELECT id FROM Salary_grades WHERE grade = ?";
		PreparedStatement salaryGradeStatement = this.connection.prepareStatement(salaryGradeSQL);
		salaryGradeStatement.setString(1, employee.getSalaryGrade());
		ResultSet salaryGradeResult = salaryGradeStatement.executeQuery();
		long salaryGradeId = -1;
		if (salaryGradeResult.next()) {
			salaryGradeId = salaryGradeResult.getLong("id");
		}

		String departmentSQL = "SELECT id FROM Departments WHERE name = ?";
		PreparedStatement departmentStatement = this.connection.prepareStatement(departmentSQL);
		departmentStatement.setString(1, employee.getDepartment());
		ResultSet departmentResult = departmentStatement.executeQuery();
		long departmentId = -1;
		if (departmentResult.next()) {
			departmentId = departmentResult.getLong("id");
		}

		statement.setLong(3, salaryGradeId);
		statement.setLong(4, departmentId);

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


	public Employee update(long id, Employee employee) throws SQLException {
		String SQL = "UPDATE Employees " +
				"SET name = ? ," +
				"job_name = ? ," +
				"salary_grade = ? ," +
				"department = ? " +
				"WHERE id = ? ";
		PreparedStatement statement = this.connection.prepareStatement(SQL);
		statement.setString(1, employee.getName());
		statement.setString(2, employee.getJobName());
		statement.setLong(5, id);
		String salaryGradeSQL = "SELECT id FROM Salary_grades WHERE grade = ?";
		PreparedStatement salaryGradeStatement = this.connection.prepareStatement(salaryGradeSQL);
		salaryGradeStatement.setString(1, employee.getSalaryGrade());
		ResultSet salaryGradeResult = salaryGradeStatement.executeQuery();
		long salaryGradeId = -1;
		if (salaryGradeResult.next()) {
			salaryGradeId = salaryGradeResult.getLong("id");
		}

		String departmentSQL = "SELECT id FROM Departments WHERE name = ?";
		PreparedStatement departmentStatement = this.connection.prepareStatement(departmentSQL);
		departmentStatement.setString(1, employee.getDepartment());
		ResultSet departmentResult = departmentStatement.executeQuery();
		long departmentId = -1;
		if (departmentResult.next()) {
			departmentId = departmentResult.getLong("id");
		}
		statement.setLong(3, salaryGradeId);
		statement.setLong(4, departmentId);

		int rowsAffected = statement.executeUpdate();
		Employee updatedEmployee = null;
		if (rowsAffected > 0) {
			updatedEmployee = this.get(id);
		}
		return updatedEmployee;
	}

	public Employee delete(long id) throws SQLException {

		String SQL = "DELETE FROM Employees WHERE id = ?";
		PreparedStatement statement = this.connection.prepareStatement(SQL);
		Employee employee = null;
		employee = this.get(id);

		statement.setLong(1, id);
		int rowsAffected = statement.executeUpdate();
		if (rowsAffected == 0) {
			employee = null;
		}
		return employee;
	}
}
