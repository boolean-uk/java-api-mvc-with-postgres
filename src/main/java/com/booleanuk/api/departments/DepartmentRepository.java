package com.booleanuk.api.departments;

import com.booleanuk.api.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {
    private MyConnection connection;

    public DepartmentRepository() throws SQLException{
        this.connection = new MyConnection();
    }
}
