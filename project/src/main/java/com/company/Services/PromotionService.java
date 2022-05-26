package com.company.Services;

import com.company.Audit;
import com.company.Config.DatabaseConfiguration;
import com.company.Entities.Promotions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PromotionService extends Audit {
    private static PromotionService instance;

    private PromotionService() {};

    public static PromotionService getInstance() {
        if (instance == null)
            instance = new PromotionService();
        return instance;
    }

    public void addPromotion(Promotions promotions) {
        String insert = "INSERT INTO promotion(code, discount, user_id) values (?,?,?);";
        audit(insert);

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)){
            preparedStatement.setString(1, promotions.getCode());
            preparedStatement.setFloat(2, promotions.getDiscount());
            preparedStatement.setInt(3, promotions.getUser_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Promotions getPromotionByCode(String code, int user_id) {
        String select = "SELECT * FROM promotion WHERE user_id=? AND code=? AND discount > 0";
        audit(select);

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, code);

            ResultSet resultSet = preparedStatement.executeQuery();
            return map(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Promotions map(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Promotions(resultSet.getString(2), resultSet.getFloat(3), resultSet.getInt(4));
        }
        return null;
    }
}
