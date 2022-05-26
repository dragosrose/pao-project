package com.company.Services;

import com.company.Config.DatabaseConfiguration;
import com.company.Entities.Category;
import com.company.Entities.Order;
import com.company.Entities.Product;
import com.company.Repositories.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Set;

public class OrderService implements IOrderService{
    private static OrderService instance;
    private OrderService() {};

    public static OrderService getInstance(){
        if(instance == null)
            instance = new OrderService();
        return instance;
    }

    @Override
    public void addOrder(Order order) {
        String insert = "INSERT INTO userorder(order_date, user_id, product_id) VALUES (?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)){
            preparedStatement.setDate(1, new java.sql.Date (order.getDate().getTime()));
            preparedStatement.setInt(2, order.getUser_id());
            preparedStatement.setInt(3, order.getProduct_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Order> getOrders() {
        String select = "SELECT * FROM userorder";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        ResultSet resultSet = null;
        try (Statement stmt = connection.createStatement()) {
            resultSet = stmt.executeQuery(select);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt(1));
                System.out.println("Date: " + resultSet.getString(2));
                System.out.println("User Id: " + resultSet.getInt(3));
                System.out.println("Product Id: " + resultSet.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Order getOrderById(int id) {
        String select = "SELECT * FROM userorder WHERE id=?";

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

    public ArrayList<Order> getOrdersByUserID(int user_id) {
        ArrayList<Order> orders = new ArrayList<Order>();

        String select = "SELECT userorder.id, product.name, category.name FROM userorder" +
                "INNER JOIN product ON userorder.product_id = product.id" +
                "INNER JOIN category ON userorder.category_id = category.id" +
                "WHERE useroder.user_id=?";


        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
            preparedStatement.setInt(1, user_id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt(1));
                System.out.println("Date: " + resultSet.getDate(2));
                System.out.println("Product: " + resultSet.getString(3));
                System.out.println("Category: " + resultSet.getString(4));
                System.out.println("----------------------------------------");
                orders.add(new Order(resultSet.getDate(2), resultSet.getInt(3), resultSet.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public void deleteOrderById(int id) {
        String delete = "DELETE FROM userorder WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Order map(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Order(resultSet.getDate(2), resultSet.getInt(3), resultSet.getInt(4));
        }
        return null;
    }


}
