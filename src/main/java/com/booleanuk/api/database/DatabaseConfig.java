package com.booleanuk.api.database;

public record DatabaseConfig(
    String dbUser,
    String dbUrl,
    String dbPassword) {
}
