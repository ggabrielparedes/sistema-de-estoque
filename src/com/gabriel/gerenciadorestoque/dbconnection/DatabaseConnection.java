package com.gabriel.gerenciadorestoque.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL  = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "C##GABRIEL"; //Username
    private static final String PASSWORD = "123"; //Password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
