package model;

import javafx.beans.property.*;

public class Customer {

    // Class variables
    private final SimpleIntegerProperty cID = new SimpleIntegerProperty();
    private final SimpleStringProperty cName = new SimpleStringProperty();
    private final SimpleStringProperty cAddress = new SimpleStringProperty();
    private final SimpleStringProperty cCity = new SimpleStringProperty();
    private final SimpleStringProperty cZip = new SimpleStringProperty();
    private final SimpleStringProperty cPhone = new SimpleStringProperty();

    // Class Constructors

    public Customer () {};

    public Customer (int id, String name, String address, String city, String zip, String phone){
        setcID(id);
        setcName(name);
        setcAddress(address);
        setcCity(city);
        setcZip(zip);
        setcPhone(phone);
    }

    // Getter and Setter methods


    public int getcID() {
        return cID.get();
    }

    public SimpleIntegerProperty cIDProperty() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID.set(cID);
    }

    public String getcName() {
        return cName.get();
    }

    public SimpleStringProperty cNameProperty() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName.set(cName);
    }

    public String getcAddress() {
        return cAddress.get();
    }

    public SimpleStringProperty cAddressProperty() {
        return cAddress;
    }

    public void setcAddress(String cAddress) {
        this.cAddress.set(cAddress);
    }

    public String getcCity() {
        return cCity.get();
    }

    public SimpleStringProperty cCityProperty() {
        return cCity;
    }

    public void setcCity(String cCity) {
        this.cCity.set(cCity);
    }

    public String getcZip() {
        return cZip.get();
    }

    public SimpleStringProperty cZipProperty() {
        return cZip;
    }

    public void setcZip(String cZip) {
        this.cZip.set(cZip);
    }

    public String getcPhone() {
        return cPhone.get();
    }

    public SimpleStringProperty cPhoneProperty() {
        return cPhone;
    }

    public void setcPhone(String cPhone) {
        this.cPhone.set(cPhone);
    }
}
