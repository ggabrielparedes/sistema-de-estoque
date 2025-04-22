package com.gabriel.gerenciadorestoque.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL  = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = ""; //Username
    private static final String PASSWORD = ""; //Password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
