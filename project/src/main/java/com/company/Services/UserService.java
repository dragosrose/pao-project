package com.company.Services;

import com.company.Config.DatabaseConfiguration;
import com.company.Entities.User;
import com.company.Repositories.Repository;

import java.sql.*;
import java.util.Objects;
import java.util.Set;

public class UserService implements IUserService{

    private static UserService instance;
    private UserService() {};

    public static UserService getInstance(){
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    @Override
    public void addUser(User user) {

        String insert = "INSERT INTO user(firstName, lastName, userName, password, role) VALUES(?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<User> getUsers() {
        String select = "SELECT * FROM user";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        ResultSet resultSet = null;
        try (Statement stmt = connection.createStatement()) {
            resultSet = stmt.executeQuery(select);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt(1));
                System.out.println("First Name: " + resultSet.getString(2));
                System.out.println("Last Name: " + resultSet.getString(3));
                System.out.println("User Name: " + resultSet.getString(4));
                System.out.println("Password: " + resultSet.getString(5));
                System.out.println("Role: " + resultSet.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getUserById(int id) {
        String select = "SELECT * FROM user WHERE id=?";

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

    @Override
    public void deleteUserById(int id) {

        String delete = "DELETE FROM user WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editUser(User user) {


        String update = "UPDATE user SET firstName=?, lastName=?, userName=?, password=?, role=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setInt(6, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User login(String userName, String password) {
        String search = "SELECT * FROM user WHERE userName=? and password=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(search)){
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return map(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private User map(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
        }
        return null;
    }


}
