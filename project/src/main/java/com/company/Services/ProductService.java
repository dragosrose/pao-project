package com.company.Services;

import com.company.Config.DatabaseConfiguration;
import com.company.Entities.Category;
import com.company.Entities.Product;
import com.company.Repositories.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static ProductService instance;

    private ProductService() {};

    public static ProductService getInstance() {
        if(instance == null)
            instance = new ProductService();
        return instance;
    }

    public void addProduct(Product product){
        String insert = "INSERT INTO product(name, description, price, stock, category_id, distributor_id) values (?,?,?,?,?,?);";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)){
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setInt(4, product.getStock());
            preparedStatement.setInt(5, product.getCategory_id());
            preparedStatement.setInt(6, product.getDistributor_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getProductsByCategoryId(int category_id){
        ArrayList<Product> products = new ArrayList<Product>();

        String select = "SELECT * FROM product WHERE category_id=?;";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
            preparedStatement.setInt(1, category_id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt(1));
                System.out.println("Name: " + resultSet.getString(2));
                System.out.println("Description: " + resultSet.getString(3));
                System.out.println("Price: " + resultSet.getFloat(4));
                System.out.println("Stock: " + resultSet.getInt(5));
                System.out.println("----------------------------------------");
                products.add(new Product(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(5), resultSet.getFloat(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> sortProducts(ArrayList<Product> products){

        return products.stream().sorted(Comparator.comparingDouble(Product::getPrice)).toList();
    }

    public Product getProductById(int id) {
        String select = "SELECT * FROM product WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return map(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getProductsByDistributorsId(int distributor_id){
        String select = "SELECT * FROM product WHERE distributor_id=?;";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        ResultSet resultSet = null;
        try (Statement stmt = connection.createStatement()) {
            resultSet = stmt.executeQuery(select);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt(1));
                System.out.println("Name: " + resultSet.getString(2));
                System.out.println("Price: " + resultSet.getFloat(3));
                System.out.println("Stock: " + resultSet.getInt(4));
                System.out.println("----------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product map(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Product p = new Product(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getInt(4), resultSet.getFloat(5));
            p.setCategory_id(resultSet.getInt(6));
            p.setDistributor_id(resultSet.getInt(7));
            return p;
        }
        return null;
    }
}
