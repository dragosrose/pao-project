package com.company.Services;

import com.company.Config.DatabaseConfiguration;
import com.company.Entities.Address;
import com.company.Repositories.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressService {

    private static AddressService instance;
    private AddressService() {};

    public static AddressService getInstance() {
        if(instance == null)
            instance = new AddressService();
        return instance;
    }

    public void addAddress(Address address){
        String insert = "INSERT INTO address(country, city, street, user_id) values (?,?,?,?);";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)){
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setInt(4, address.getUser_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Address getAddressByUserId(int user_id) {
        String select = "SELECT * FROM address WHERE user_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
            preparedStatement.setInt(1, user_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return map(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Address map(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Address(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
        }
        return null;
    }

}
