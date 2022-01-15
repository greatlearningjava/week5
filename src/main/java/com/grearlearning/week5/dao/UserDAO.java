package com.grearlearning.week5.dao;

import com.grearlearning.week5.entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO implements IUserDAO {

    private static final String CREATE_USER_TABLE =
            "create table users (userId INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    " firstName varchar(50) , lastName varchar(50), email varchar(50))";
    private static final String SELECT_TOP_1_FROM_USERS =
            "Select * from users LIMIT 1";
    private Connection connection = null;


    public UserDAO(Connection connection) {
        this.connection = connection;

    }

    /*
    This method will check if the table exist in DB.
    If does not exist, then it will create
     */
    @Override
    public boolean isUSerTableExist() {
        Statement statement = null;
        boolean exists = false;
        try {
            statement = connection.createStatement();
            exists = statement.execute(SELECT_TOP_1_FROM_USERS);
            statement.close();
            return exists;
        } catch (SQLException e) {
            try {
                statement.execute(CREATE_USER_TABLE);
                statement.close();
                return exists;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return exists;

    }

    @Override
    public void create(User user) {
        PreparedStatement preparedStatement = null;
        try {
            String insertSql = "insert into users(firstName, lastName, email) values(?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            System.out.println("Is it inserted: " + preparedStatement.execute());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(String id) {
        boolean bool = false;
        PreparedStatement preparedStatement = null;
        User user = new User();
        try {
            String deleteSql = "delete from users where userId = ? ";
            preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setString(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if(rowsDeleted > 0 ){
                System.out.println("Deleted details of following user: " + id);
                bool = true;
            }else{
                System.out.println("Unable to delete user: " + id);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

   public boolean update(User user) {
       boolean bool = false;
       PreparedStatement preparedStatement = null;
       try {
           String updateSql = "UPDATE users set firstName = ? , lastName = ? , email = ? where userId = ? ";
           preparedStatement = connection.prepareStatement(updateSql);
           preparedStatement.setString(1, user.getFirstName());
           preparedStatement.setString(2, user.getLastName());
           preparedStatement.setString(3, user.getEmail());
           preparedStatement.setInt(4, user.getUserId());
           int rowsUpdated = preparedStatement.executeUpdate();
           if(rowsUpdated > 0){
               System.out.println("An existing user was updated successfully! - " + user.getUserId());
               bool = true;
           }else{
               System.out.println("Unable to update user: " + user.getUserId());

           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return bool;

    }


    @Override
    public User display(String userId) {
        PreparedStatement preparedStatement = null;
        User user = new User();
        try {
            String selectSql = "select * from users where userId = ? ";
            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setUserId(resultSet.getInt(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    @Override
    public ArrayList<User> displayAll() {
        ArrayList<User> userArrayList = new ArrayList<>();
        Statement statement = null;
        try {
            String selectSql = "select * from users ";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                userArrayList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userArrayList;
    }
}

