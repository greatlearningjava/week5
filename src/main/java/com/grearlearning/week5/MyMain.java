package com.grearlearning.week5;

import com.grearlearning.week5.dao.UserDAO;
import com.grearlearning.week5.entity.User;
import com.grearlearning.week5.service.DBService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MyMain {

    public static void main(String[] args) {
        System.out.println("!!!!! Welcome to User Crud Operations");
        DBService service = DBService.INSTANCE;
        Connection conn =  service.getConnection();
        UserDAO userDAO = new UserDAO(conn);
        System.out.println("isUSerTableExist:" + userDAO.isUSerTableExist());
        String message = "Enter the operation that you want to perform";
        performOperation(conn,userDAO, message);
        if (conn != null){
            try {
                conn.close();
                System.out.println("Connection has been closed successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void performOperation(Connection connection, UserDAO userDAO, String message) {
        System.out.println(message);
        System.out.println("1. Registration");
        System.out.println("2. Update");
        System.out.println("3. Display Data by Id");
        System.out.println("4. Delete");
        System.out.println("5. Display all Data");
        System.out.println("0. Exit");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter : ");
        int option = scanner.nextInt();
        switch (option){
            case 0:
                System.out.println("Going to exit");
                scanner.close();
                return;
            case 1:
                System.out.println("Initiated the Registration process");
                User user = new User();
                System.out.print("Please enter firstName : ");
                String firstName = scanner.next();
                user.setFirstName(firstName);
                System.out.print("Please enter lastName : ");
                String lastName = scanner.next();
                user.setLastName(lastName);
                System.out.print("Please enter valid EmailId : ");
                String email = scanner.next();
                user.setEmail(email);
                userDAO.create(user);
                performOperation(connection, userDAO, "Please select next operation");
                return;
            case 2:
                System.out.println("Going to perform update");
                System.out.println("Please enter UserId, firstName, lastName, emailId respectively");
                User user1 = new User(scanner.nextInt(), scanner.next(), scanner.next(), scanner.next());
                userDAO.update(user1);
                performOperation(connection, userDAO, "Please select next operation");
                return;
            case 3:
                System.out.println("Enter the userId:");
                String userId = scanner.next();
                user = userDAO.display(userId);
                System.out.println("Retrieved from DB : " + user);
                performOperation(connection, userDAO, "Please select next operation");
                return;
            case 4:
                System.out.println("Enter the userId to be deleted:");
                userId = scanner.next();
                userDAO.delete(userId);
                performOperation(connection, userDAO, "Please select next operation");
                return;
            case 5:
                System.out.println("Display all the data:");
                ArrayList<User> userlist = userDAO.displayAll();
                //Using Java 8 API
                userlist.stream().forEach(System.out::println);
                performOperation(connection, userDAO, "Please select next operation");
                return;
            default:
                System.out.println("Invalid operation, Bye !!!");
                scanner.close();
                return;

        }
    }
}
