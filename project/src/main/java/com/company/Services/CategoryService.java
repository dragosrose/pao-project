package com.company.Services;

import com.company.Audit;
import com.company.Config.DatabaseConfiguration;
import com.company.Entities.Category;
import com.company.Repositories.Repository;

import java.sql.*;
import java.util.Set;

public class CategoryService extends Audit implements ICategoryService {
    private static CategoryService instance;

    private CategoryService() {

    };

    public static CategoryService getInstance() {
        if (instance == null)
            instance = new CategoryService();
        return instance;
    }

    @Override
    public void addCategory(Category category) {
        String insert = "INSERT INTO category(name) VALUES(?)";
        audit(insert);

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, category.getName());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Set<Category> getCategories() {
        String select = "SELECT * FROM category";
        audit(select);

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        ResultSet resultSet = null;
        try (Statement stmt = connection.createStatement()) {
            resultSet = stmt.executeQuery(select);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt(1));
                System.out.println("Name: " + resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Category getCategoryById(int id) {
        String select = "SELECT * FROM category WHERE id=?";
        audit(select);

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
    public void updateCategory(String name, int id) {
        String update = "UPDATE category SET name=? WHERE id=?";
        audit(update);

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategoryById(int id) {
        String delete = "DELETE FROM category WHERE id=?";
        audit(delete);

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Category map(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Category(resultSet.getInt(1), resultSet.getString(2));
        }
        return null;
    }
}
