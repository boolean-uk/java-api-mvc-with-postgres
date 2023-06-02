package com.booleanuk.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public interface Database {
    PreparedStatement statement(String sqlQuery);
}
