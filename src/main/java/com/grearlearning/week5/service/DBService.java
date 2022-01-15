package com.grearlearning.week5.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DBService {
    INSTANCE;

    public Connection getConnection(){

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Minimum!1783");
            System.out.println(connection);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  connection;
    }
}
