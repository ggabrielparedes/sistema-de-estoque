package com.gabriel.gerenciadorestoque.dbconnection;

import oracle.jdbc.internal.XSCacheOutput;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Product {
    private String product_name;
    private int product_quantity;
    private String manufacturer;
    private String codebar;
    private Integer product_id;
    
    public Product(String product_name, int quantity, String manufacturer, String codebar) {
        this.product_name = product_name;
        this.product_quantity = quantity;
        this.manufacturer = manufacturer;
        this.codebar = codebar;
    }

    public Product(String codebar) {
        this.codebar = codebar;
        loadProduct(codebar);
    }

    public String getProductName() {
        return product_name;
    }

    public Integer getQuantity() {
        return product_quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getCodeBar() {
        return codebar;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public void setQuantity(int quantity) {
        this.product_quantity = quantity;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setCodeBar(String codebar) {
        this.codebar = codebar;
    }

    public void addProduct() {
        String SQL = "INSERT INTO PRODUTOS (nm_produto, qt_produto, nm_fabricante, cd_barras) VALUES (?, ?, ?, ?)";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ){
            if(Product.verifyCodeBar(codebar)) {
                System.out.println("Este produto ja existe.");
                return;
            }
            preparedStatement.setString(1, product_name);
            preparedStatement.setInt(2, product_quantity);
            preparedStatement.setString(3, manufacturer);
            preparedStatement.setString(4, codebar);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) System.out.println("Produto adicionado com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeProduct(String codebar) {
        String SQL = "DELETE FROM PRODUTOS WHERE cd_barras = ?";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ){
            if(Product.verifyCodeBar(codebar)) {
                preparedStatement.setString(1, codebar);
                preparedStatement.executeUpdate();
                System.out.println("Produto removido com sucesso.");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static HashMap<Integer, List<String>> listProducts(Integer offset) {
        String SQL = "SELECT nm_produto, qt_produto, nm_fabricante, cd_barras FROM Produtos OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";
        ArrayList<String> list = new ArrayList<String>();
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            preparedStatement.setInt(1, offset);
            ResultSet resultset = preparedStatement.executeQuery();
            int count = 1;
            HashMap<Integer, List<String>> map = new HashMap<>();
            while(resultset.next()) {
                map.put(count, new ArrayList<>());
                map.get(count).add(resultset.getString(1));
                map.get(count).add(String.valueOf(resultset.getInt(2)));
                map.get(count).add(resultset.getString(3));
                map.get(count).add(resultset.getString(4));
                count++;
                //System.out.print(resultset.getInt(1) + " w " + resultset.getString(2) + "  " + resultset.getInt(3) + "  " + resultset.getString(4) + "  " + resultset.getString(4));
                //System.out.println(resultset.getString(5)); //ta retornando toda linha, fazer um for para iterar quantidade de linhas que quero pegar, nao é perfeito porém funcional
                //fazer uma funcao para pegar o id do produto selecionado e editar ele
            }
            return map;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer getMaxRows() {
        String SQL = "SELECT COUNT(*) FROM PRODUTOS";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProduct(String codebar) {
        String SQL = "SELECT id, nm_produto, qt_produto, nm_fabricante FROM PRODUTOS WHERE cd_barras = ?";
        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ){
            preparedStatement.setString(1, codebar);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                product_id = resultSet.getInt(1);
                product_name = resultSet.getString(2);
                product_quantity = resultSet.getInt(3);
                manufacturer = resultSet.getString(4);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } // carregar produto aqui

    public void saveChanges() {
        String SQL = "UPDATE Produtos SET nm_produto = ?, qt_produto = ?, nm_fabricante = ?, cd_barras = ? WHERE id = ?";
        try(
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ){
            preparedStatement.setString(1, product_name);
            preparedStatement.setInt(2, product_quantity);
            preparedStatement.setString(3, manufacturer);
            preparedStatement.setString(4, codebar);
            preparedStatement.setInt(5, product_id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean verifyCodeBar(String codebar) {
        String SQL = "SELECT * FROM PRODUTOS WHERE cd_barras = ?";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ){
            preparedStatement.setString(1, codebar);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
