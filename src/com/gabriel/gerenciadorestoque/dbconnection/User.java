package com.gabriel.gerenciadorestoque.dbconnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String name;
    private String password;
    private String email;
    private int id;

    public User(String name, String password, String email) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id =  id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean addUser() {
        String SQL = "INSERT INTO USUARIOS (nm_usuario, pw_usuario, email_usuario) VALUES (?, ?, ?)";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ){
            if(verifyUser(email)) {
                System.out.println("Email ja existente");
                return false;
            }
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            int rowsInserted = preparedStatement.executeUpdate();
            if(rowsInserted > 0) System.out.println("Usuario cadastrado com sucesso");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean loginUser() {
        String SQL = "SELECT id_usuario, nm_usuario, pw_usuario, email_usuario FROM Usuarios WHERE email_usuario = ?";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ){
            if(!verifyUser(email)) {
                return false;
            } else {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    if(resultSet.getString("pw_usuario").equals(password)) {
                        id = resultSet.getInt("id_usuario");
                        name = resultSet.getString("nm_usuario");
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void saveChanges(int id_user) {
        String SQL = "UPDATE Usuarios SET nm_usuario = ?, pw_usuario = ?, email_usuario = ? WHERE id_usuario = ?";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, id_user);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyUser(String value) {
        String SQL = "SELECT email_usuario FROM USUARIOS WHERE email_usuario = ?";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
