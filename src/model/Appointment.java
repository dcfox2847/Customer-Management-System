package model;

import javafx.beans.property.*;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class Appointment {

    // Class Variables
    private final SimpleIntegerProperty aID = new SimpleIntegerProperty();
    private final SimpleIntegerProperty aCustID = new SimpleIntegerProperty();
    private final SimpleStringProperty aCustName = new SimpleStringProperty();
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

    public int getaCustID() { return aCustID.get(); }

    public SimpleIntegerProperty aCustIDProperty() { return aCustID; }

    public void setaCustID(int aCustID) { this.aCustID.set(aCustID); }

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

    public String getaCustName() { return aCustName.get(); }

    public SimpleStringProperty aCustNameProperty() { return aCustName; }

    public void setaCustName(String aCustName) { this.aCustName.set(aCustName); }

    // Additional methods for date and time formatting

    public String getaStart(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(this.aStartTime.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        StringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        String sDate = utcDate.toLocalDateTime().toString();
        return sDate;
    }

    public String getaDateOnly(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(this.aStartTime.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        StringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        String sDate = utcDate.toLocalDateTime().toString();
        return sDate;
    }

    public String getaEnd(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(this.aEndTime.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        StringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        String newDate = date.get();
        String formatDate = newDate.substring(0,10);
        return formatDate;
    }

    public LocalDate getDateOnly() {
        Timestamp ts = Timestamp.valueOf(this.aStartTime.get());
        ZonedDateTime zdt;
        ZoneId zid;
        LocalDate ld;
        if(this.aLocation.get().equals("New York")) {
            zid = ZoneId.of("America/New_York");
        } else if(this.aLocation.get().equals("Los Angeles")) {
            zid = ZoneId.of("America/Los_Angeles");
        } else {
            zid = ZoneId.of("Europe/London");
        }
        zdt = ts.toLocalDateTime().atZone(zid);
        ld = zdt.toLocalDate();
        return ld;
    }

    public String getTimeOnly() {
        Timestamp ts = Timestamp.valueOf(this.aStartTime.get());
        ZonedDateTime zdt;
        ZoneId zid;
        LocalTime lt;
        if(this.aLocation.get().equals("New York")) {
            zid = ZoneId.of("America/New_York");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().minusHours(4);
        } else if(this.aLocation.get().equals("Phoenix")) {
            zid = ZoneId.of("America/Los_Angeles");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().minusHours(8);
        } else {
            zid = ZoneId.of("Europe/London");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().plusHours(1);
        }
        int rawH = Integer.parseInt(lt.toString().split(":")[0]);
        if(rawH > 12) {
            rawH -= 12;
        }
        String ampm;
        if(rawH < 9 || rawH == 12) {
            ampm = "PM";
        } else {
            ampm = "AM";
        }
        String time = rawH + ":00 " + ampm;
        return time;
    }

    public String get15Time() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt = LocalDateTime.parse(this.aStartTime.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        DateTimeFormatter tFormatter = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime localTime = LocalTime.parse(utcDate.toString().substring(11,16), tFormatter);
        return localTime.toString();
    }

    /*
    INSERT THE METHODS NEEDED FOR CHANGING THE DATES AND TIMES HERE
     */
}
