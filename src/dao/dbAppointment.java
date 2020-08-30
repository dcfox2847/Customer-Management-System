package dao;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import com.mysql.cj.xdevapi.Statement;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.*;
import dao.dbConnection;
import javafx.scene.control.Alert;
import model.Appointment;
import model.Customer;
import model.User;
import view.LoginController;

import static dao.dbConnection.stmt;

public class dbAppointment {

    private static String user;

    /*
    * CLASS FUNCTIONS AND METHODS
    * */

    // Function to get all users appointments for the month
    public static ObservableList<Appointment> getMonthlyApt(int id) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment apt;
        Customer cust = new Customer();
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        try{
            String monthlyQuery = "SELECT * FROM appointment WHERE userId = '" + id + "'AND " + "start >= '" + begin
                    + "' AND start <= '" + end + "'";
            ResultSet monthlyResults = stmt.executeQuery(monthlyQuery);
            while(monthlyResults.next()){
                apt = new Appointment(monthlyResults.getInt("appointmentId"), monthlyResults.getInt("customerId"),
                        monthlyResults.getString("start"), monthlyResults.getString("end"),
                        monthlyResults.getString("title"), monthlyResults.getString("description"),
                        monthlyResults.getString("location"), monthlyResults.getString("contact"));
                appointments.add(apt);
            }
            System.out.println("Appointments for the month have been returned!");
            System.out.println("List size: " + appointments.size());
        }catch (SQLException ex){
            System.out.println("SQLException: (monthly query) " + ex.getMessage() + "!!!");
            return null;
        }
        // Start Test
        for (Appointment item : appointments) {
            try {
                ResultSet custNameResult = stmt.executeQuery("SELECT * FROM customer WHERE customerId ='" + item.getaCustID() + "'");
                while (custNameResult.next()) {
                    if (custNameResult.getInt("customerId") == item.getaCustID()) {
                        item.setaCustName(custNameResult.getString("customerName"));
                    }
                }
            } catch (SQLException exc) {
                System.out.println("Exception in the customer portion: " + exc.getMessage());
            }
        }
        // End Test
        return appointments;
    }

     // Function to get all users appointments for the week
    public static ObservableList<Appointment> getWeeklyApt(int id){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment apt;
        Customer cust = new Customer();
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusWeeks(1);
        try{
            String weeklyQuery = "SELECT * FROM appointment WHERE userId = '" + id + "'AND " + "start >= '" + begin
                    + "' AND start <= '" + end + "'";
            ResultSet weeklyResults = stmt.executeQuery(weeklyQuery);
            while(weeklyResults.next()){
                apt = new Appointment(weeklyResults.getInt("appointmentId"), weeklyResults.getInt("customerId"),
                        weeklyResults.getString("start"), weeklyResults.getString("end"),
                        weeklyResults.getString("title"), weeklyResults.getString("description"),
                        weeklyResults.getString("location"), weeklyResults.getString("contact"));
                appointments.add(apt);
            }
            System.out.println("Appointments for the month have been returned!");
            System.out.println("List size: " + appointments.size());
        }catch (SQLException ex){
            System.out.println("SQLException: (monthly query) " + ex.getMessage() + "!!!");
            return null;
        }
        // Start Test
        for (Appointment item : appointments) {
            try {
                ResultSet custNameResult = stmt.executeQuery("SELECT * FROM customer WHERE customerId ='" + item.getaCustID() + "'");
                while (custNameResult.next()) {
                    if (custNameResult.getInt("customerId") == item.getaCustID()) {
                        item.setaCustName(custNameResult.getString("customerName"));
                    }
                }
            } catch (SQLException exc) {
                System.out.println("Exception in the customer portion: " + exc.getMessage());
            }
        }
        // End Test
        return appointments;
    }

    // Function to return any appointments scheduled within the next 15 minutes.
    public static ObservableList<Appointment> get15MinuteApt(int id){
//        System.out.println("TESTING FROM INSIDE THE GET15MINUTES METHOD");
        ObservableList<Appointment> apt15Minutes = FXCollections.observableArrayList();
        Appointment apt;
        LocalDateTime now = LocalDateTime.now();
//        System.out.println("TIME RIGHT NOW: " + now);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
//        System.out.println("zonedDateTime: " + zonedDateTime);
        LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        String lDateTime = localDateTime.format(dtf);
        LocalDateTime localDateTime1 = localDateTime.plusMinutes(15);
        String lDateTime1 = localDateTime1.format(dtf);
        user = dbUser.getCurrentUser().getUserName();
        try{
            String fifteenMinQuery =  "SELECT * FROM appointment WHERE userID = '" + id + "' AND start BETWEEN '" + lDateTime + "' AND '" + lDateTime1 + "'";
            ResultSet resultSet = stmt.executeQuery(fifteenMinQuery);
            if(resultSet.next()){
                apt = new Appointment(resultSet.getInt("appointmentId"), resultSet.getInt("customerId"),
                        resultSet.getString("start"), resultSet.getString("end"), resultSet.getString("title"),
                        resultSet.getString("description"), resultSet.getString("location"), resultSet.getString("contact"));
                apt15Minutes.add(apt);
            }
        }catch (SQLException ex){
            System.out.println("SQLException (in the 15 minutes function): " + ex.getMessage());
            return null;
        }
        return apt15Minutes;
    }

    // Function to change time to UTC
    public static LocalDateTime changeToUtc(String stringTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.parse(stringTime, formatter);
        return time.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }
    // Function to change time from UTC to local
    public static LocalDateTime changeFromUtc(LocalDateTime stringTime, String timeZone){
        ZonedDateTime zonedUTC = stringTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedEST = zonedUTC.withZoneSameInstant(ZoneId.of(timeZone));
        return LocalDateTime.from(zonedEST);
    }

    // Function to add a new appointment to the database
    public static boolean addAppointment(int id, String type, String description, String contact, String location, String date, String time){
        String preConvertedString = date + " " + time;  // The local time and date as String
        LocalDateTime dateTimeString = changeToUtc(preConvertedString); // Time and date for UTC as LocalDateTime class
        try{
            String query = "INSERT INTO appointment SET customerId='" + id + "', userId='" + LoginController.currUser.getUserID() + "', title='" + type +
                    "', description='" + description + "', location='" + location + "', contact='" + contact + "', type='" + type + "', url='', start='" + dateTimeString +
                    "', end='" + preConvertedString + "', createDate=NOW(), createdBy='test', lastUpdateBy='tester'";
            int added = stmt.executeUpdate(query);
            if(added == 1){
                System.out.println("Appointment added: " + added);
                return true;
            }
        } catch (SQLException ex){
            System.out.println("SQLException: " +  ex.getMessage());
        }
        return false;
    }

    // Function to modify an existing appointment
    public static boolean modifyAppointment(int id, String type, String contact, String location, String date, String time){
        String preConvertedString = date + " " + time;
        LocalDateTime dateTimeString = changeToUtc(preConvertedString);
        try{
            //SQL UPDATE STATEMENT
            String query = " UPDATE appointment SET title='" + type + "', description='" + type + "', contact='" + contact + "', location='" + location +
                    "', start='" + dateTimeString + "' WHERE appointmentId=" + id;
            int added = stmt.executeUpdate(query);
            if(added==1){
                System.out.println("Appointment has been modified.");
                return true;
            }
        } catch (SQLException ex){
            System.out.println("SQLException: " + ex);
        }
        return false;
    }

    // Function to delete an appointment
    public static boolean deleteAppointment(int id){
        String query = "DELETE FROM appointment WHERE appointmentId = " + id;
        try {
            int update = stmt.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Cannot delete Appointment: " + e.getMessage());
        }
        return false;
    }

    // Function to check to for overlapping appointments
    public static boolean overlappingAppointment (int id, String location,  String date, String time){
        String preConvertedString = date + " " + time;
        LocalDateTime dateTimeString = changeToUtc(preConvertedString);
        try {
            String query1 = "SELECT * FROM appointment "
                    + "WHERE start = '" + dateTimeString + "' AND location = '" + location + "'";
            ResultSet results = stmt.executeQuery(query1);
            if(results.next()) {
                if(results.getInt("appointmentId") == id) {
                    return false;
                }
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return true;
        }
    }

    // Function to check for appointment outside of business hours
    public static boolean outsideOfBusinessHours(String location, String date, String time) {
        try {
            int weekendReturn = 0;
            String queryWeekend = "SELECT DAYOFWEEK('" + date + "') AS dayNumber;";
            ResultSet weekendResultSet = stmt.executeQuery(queryWeekend);
            if (weekendResultSet.next()) {
                weekendReturn = weekendResultSet.getInt("dayNumber");
                System.out.println("SQL number for the day: " + weekendReturn);
            }
            if (weekendReturn == 1 || weekendReturn == 6){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL ERROR: " + e.getMessage());
            return true;
        }

    }
}
