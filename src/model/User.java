package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {

    // class variables
    private final SimpleStringProperty userName = new SimpleStringProperty();
    private final SimpleIntegerProperty userID = new SimpleIntegerProperty();
    private final SimpleStringProperty userPassword = new SimpleStringProperty();

    // class constructors
    public User() {};

    public User (String userName){
        setUserName(userName);
    }

    public User (String userName, int userID, String userPassword){
        setUserName(userName);
    }

    // Getter and Setter methods
    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public int getUserID() { return userID.get(); }

    public SimpleIntegerProperty userIDProperty() { return userID; }

    public void setUserID(int userID) { this.userID.set(userID); }

    public String getUserPassword() { return userPassword.get(); }

    public SimpleStringProperty userPasswordProperty() { return userPassword; }

    public void setUserPassword(String userPassword) { this.userPassword.set(userPassword); }
}
