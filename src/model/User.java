package model;

import javafx.beans.property.SimpleStringProperty;

public class User {

    // class variables
    private final SimpleStringProperty userName = new SimpleStringProperty();

    // class constructors
    public User() {};

    public User (String userName){
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
}
