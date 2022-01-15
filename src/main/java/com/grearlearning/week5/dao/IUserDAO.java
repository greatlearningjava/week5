package com.grearlearning.week5.dao;

import com.grearlearning.week5.entity.User;

import java.util.ArrayList;

public interface IUserDAO {
    /*
        This method will check if the table exist in DB.
        If does not exist, then it will create
         */
    boolean isUSerTableExist();

    void create(User user);

    boolean delete(String id);

    User display(String userId);

    ArrayList<User> displayAll();
}
