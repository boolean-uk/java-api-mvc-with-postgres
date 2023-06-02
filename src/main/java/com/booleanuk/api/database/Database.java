package com.booleanuk.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Database {
//    PreparedStatement statement(String sqlQuery);
    Connection connection() throws SQLException;
}
