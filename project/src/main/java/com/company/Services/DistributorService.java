package com.company.Services;

import com.company.Config.DatabaseConfiguration;
import com.company.Entities.Distributor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DistributorService {
    private static DistributorService instance;

    private DistributorService() {};

    public static DistributorService getInstance() {
        if(instance == null)
            instance = new DistributorService();
        return instance;
    }

    public void addDistributor(Distributor distributor) {
        String insert = "INSERT INTO distributor(name) values(?);";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)){
            preparedStatement.setString(1, distributor.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
