package com.booleanuk.api.salary;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.postgresql.ds.PGSimpleDataSource;

public class SalaryRepository {
	DataSource datasource;
	String dbUser;
	String dbURL;
	String dbPassword;
	String dbDatabase;
	Connection connection;

	public SalaryRepository() throws SQLException {
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

	public List<Salary> getAll() throws SQLException {
		List<Salary> salaryList = new ArrayList<>();
		PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salary_grades");
		ResultSet results = statement.executeQuery();
		while (results.next()) {
			Salary salary = new Salary(results.getLong("id"), results.getString("grade"), results.getInt("min_salary"), results.getInt("max_salary"));
			salaryList.add(salary);
		}
		return salaryList;
	}

	public Salary get(long id) throws SQLException {
		PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salary_grades WHERE id = ?");
		statement.setLong(1, id);
		ResultSet results = statement.executeQuery();
		Salary salary = null;
		if (results.next()) {
			salary = new Salary(results.getLong("id"), results.getString("grade"), results.getInt("min_salary"), results.getInt("max_salary"));
		}
		return salary;
	}

	public Salary add(Salary salary) throws SQLException {
		String SQL = "INSERT INTO Salary_grades (grade, min_salary, max_salary) VALUES (?, ?, ?)";
		PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, salary.getGrade());
		statement.setInt(2, salary.getMinSalary());
		statement.setInt(3, salary.getMaxSalary());

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
			salary.setId(newId);
		} else {
			salary = null;
		}
		return salary;
	}

	public Salary update(long id, Salary salary) throws SQLException {
		String SQL = "UPDATE Salary_grades SET grade = ?, min_salary = ?, max_salary = ? WHERE id = ?";
		PreparedStatement statement = this.connection.prepareStatement(SQL);
		statement.setString(1, salary.getGrade());
		statement.setInt(2, salary.getMinSalary());
		statement.setInt(3, salary.getMaxSalary());
		statement.setLong(4, id);

		int rowsAffected = statement.executeUpdate();
		if (rowsAffected > 0) {
			return salary;
		} else {
			return null; // If no rows were affected, return null indicating failure
		}
	}

	public Salary delete(long id) throws SQLException {
		String SQL = "DELETE FROM Salary_grades WHERE id = ?";
		PreparedStatement statement = this.connection.prepareStatement(SQL);
		statement.setLong(1, id);

		int rowsAffected = statement.executeUpdate();
		if (rowsAffected > 0) {
			return this.get(id); // Return the deleted salary if successful
		} else {
			return null; // If no rows were affected, return null indicating failure
		}
	}


}
