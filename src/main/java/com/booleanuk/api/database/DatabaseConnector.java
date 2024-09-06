package com.booleanuk.api.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.postgresql.ds.PGSimpleDataSource;

abstract public class DatabaseConnector {
  public static Connection getConnection() throws SQLException {
    DatabaseConfig config = DatabaseConnector.readConfig();
    return DatabaseConnector.connectToDatabase(config);
  }

  private static DatabaseConfig readConfig() throws SQLException {
    try {
      InputStream inputStream = new FileInputStream("src/main/resources/config.properties");
      Properties properties = new Properties();
      properties.load(inputStream);

      String dbUser = properties.getProperty("db.user");
      String dbUrl = properties.getProperty("db.url");
      String dbPassword = properties.getProperty("db.password");

      inputStream.close();
      return new DatabaseConfig(dbUser, dbUrl, dbPassword);
    } catch (IOException e) {
      throw new SQLException();
    }
  }

  private static Connection connectToDatabase(DatabaseConfig config) throws SQLException {
    final String url = String.format("%s?user=%s&password=%s", config.dbUrl(),
        config.dbUser(), config.dbPassword());

    final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setURL(url);

    return dataSource.getConnection();
  }
}
