package model;

import javafx.beans.property.*;


public class Appointment {

    // Class Variables
    private final SimpleIntegerProperty aID = new SimpleIntegerProperty();
    private final SimpleIntegerProperty aCustID = new SimpleIntegerProperty();
    private final SimpleStringProperty aStartTime = new SimpleStringProperty();
    private final SimpleStringProperty aEndTime = new SimpleStringProperty();
    private final SimpleStringProperty aTitle = new SimpleStringProperty();
    private final SimpleStringProperty aDesc = new SimpleStringProperty();
    private final SimpleStringProperty aLocation = new SimpleStringProperty();
    private final SimpleStringProperty aContact = new SimpleStringProperty();

    // Object constructors
    public Appointment() {}

    public Appointment(int id, int custId, String start, String end, String title, String description, String location, String contact){
        setaID(id);
        setaCustID(custId);
        setaStartTime(start);
        setaEndTime(end);
        setaTitle(title);
        setaDesc(description);
        setaLocation(location);
        setaContact(contact);

    }

    // Getter and Setter methods
    public int getaID() {
        return aID.get();
    }

    public SimpleIntegerProperty aIDProperty() {
        return aID;
    }

    public void setaID(int aID) {
        this.aID.set(aID);
    }

    public int getaCustID() {
        return aCustID.get();
    }

    public SimpleIntegerProperty aCustIDProperty() {
        return aCustID;
    }

    public void setaCustID(int aCustID) {
        this.aCustID.set(aCustID);
    }

    public String getaStartTime() {
        return aStartTime.get();
    }

    public SimpleStringProperty aStartTimeProperty() {
        return aStartTime;
    }

    public void setaStartTime(String aStatTime) {
        this.aStartTime.set(aStatTime);
    }

    public String getaEndTime() {
        return aEndTime.get();
    }

    public SimpleStringProperty aEndTimeProperty() {
        return aEndTime;
    }

    public void setaEndTime(String aEndTime) {
        this.aEndTime.set(aEndTime);
    }

    public String getaTitle() {
        return aTitle.get();
    }

    public SimpleStringProperty aTitleProperty() {
        return aTitle;
    }

    public void setaTitle(String aTitle) {
        this.aTitle.set(aTitle);
    }

    public String getaDesc() {
        return aDesc.get();
    }

    public SimpleStringProperty aDescProperty() {
        return aDesc;
    }

    public void setaDesc(String aDesc) {
        this.aDesc.set(aDesc);
    }

    public String getaLocation() {
        return aLocation.get();
    }

    public SimpleStringProperty aLocationProperty() {
        return aLocation;
    }

    public void setaLocation(String aLocation) {
        this.aLocation.set(aLocation);
    }

    public String getaContact() {
        return aContact.get();
    }

    public SimpleStringProperty aContactProperty() {
        return aContact;
    }

    public void setaContact(String aContact) {
        this.aContact.set(aContact);
    }

    // Additional methods for date and time formatting

    /*
    INSERT THE METHODS NEEDED FOR CHANGING THE DATES AND TIMES HERE
     */
}
