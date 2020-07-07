package dao;

import java.sql.*;
import model.User;
import dao.dbConnection;

import javax.swing.plaf.nimbus.State;


public class dbUser {

    // class variables
    private static User currentUser;
    Statement userStatement;

    // class constructors
    public dbUser(){};

    // class getter and setter methods
    public static User getCurrentUser() { return currentUser; }

    public static void setCurrentUser(User currentUser) { dbUser.currentUser = currentUser; }

    // class methods
    public static Boolean userLogin(String username, String password){
        try {
            String query = "SELECT * FROM user WHERE userName='" + username + "' AND password='" + password + "'";
            ResultSet userResults = dbConnection.stmt.executeQuery(query);
            if(userResults.next()){
                currentUser = new User();
                currentUser.setUserName(userResults.getString("userName"));
                currentUser.setUserID(userResults.getInt("userId"));
                currentUser.setUserPassword(userResults.getString("password"));
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            System.out.println("Exception: " + e);
            return false;
        }

    }

}
