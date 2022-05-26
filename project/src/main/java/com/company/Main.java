package com.company;

import com.company.Services.Service;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        Service service = Service.getInstance();
        service.mainPanel();

    }
}
