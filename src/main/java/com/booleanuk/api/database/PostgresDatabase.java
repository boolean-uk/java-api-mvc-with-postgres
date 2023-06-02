package com.booleanuk.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PostgresDatabase implements Database {
    @Override
    public PreparedStatement statement(String sqlQuery) {
        return null;
    }
}
