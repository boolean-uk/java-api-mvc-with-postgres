package com.booleanuk.api.department;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.postgresql.ds.PGSimpleDataSource;

public class DepartmentRepository {
	DataSource datasource;
	String dbUser;
	String dbURL;
	String dbPassword;
	String dbDatabase;
	Connection connection;

	public DepartmentRepository() throws SQLException {
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

	public List<Department> getAll() throws SQLException {
		List<Department> departmentList = new ArrayList<>();
		PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments");
		ResultSet results = statement.executeQuery();
		while (results.next()) {
			Department department = new Department(results.getLong("id"), results.getString("name"), results.getString("location"));
			departmentList.add(department);
		}
		return departmentList;
	}

	public Department get(long id) throws SQLException {
		PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments WHERE id = ?");
		statement.setLong(1, id);
		ResultSet results = statement.executeQuery();
		Department department = null;
		if (results.next()) {
			department = new Department(results.getLong("id"), results.getString("name"), results.getString("location"));
		}
		return department;
	}

	public Department add(Department department) throws SQLException {
		String SQL = "INSERT INTO Departments (name, location) VALUES (?, ?)";
		PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, department.getName());
		statement.setString(2, department.getLocation());

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
			department.setId(newId);
		} else {
			department = null;
		}
		return department;
	}

	public Department update(long id, Department department) throws SQLException {
		String SQL = "UPDATE Departments SET name = ?, location = ? WHERE id = ?";
		PreparedStatement statement = this.connection.prepareStatement(SQL);
		statement.setString(1, department.getName());
		statement.setString(2, department.getLocation());
		statement.setLong(3, id);

		int rowsAffected = statement.executeUpdate();
		if (rowsAffected > 0) {
			return department;
		} else {
			return null; // If no rows were affected, return null indicating failure
		}
	}

	public Department delete(long id) throws SQLException {
		String SQL = "DELETE FROM Departments WHERE id = ?";
		PreparedStatement statement = this.connection.prepareStatement(SQL);
		statement.setLong(1, id);

		int rowsAffected = statement.executeUpdate();
		if (rowsAffected > 0) {
			return this.get(id); // Return the deleted department if successful
		} else {
			return null; // If no rows were affected, return null indicating failure
		}
	}
}
