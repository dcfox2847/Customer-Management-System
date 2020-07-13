package dao;

import java.sql.*;
import java.time.*;
import java.util.Locale;

import javafx.collections.*;
import dao.dbConnection;
import model.Appointment;
import model.Customer;
import model.User;

import static dao.dbConnection.stmt;

public class dbAppointment {

    private static String user;

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

    // Function to return any appointmens scheduled within the next 15 minutes.

    public static ObservableList<Appointment> get15MinuteApt(){
        ObservableList<Appointment> apt15Minutes = FXCollections.observableArrayList();
        Appointment apt;
        LocalDateTime now = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
        LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime localDateTime1 = localDateTime.plusMinutes(15);
        user = dbUser.getCurrentUser().getUserName();
        try{
            String fifteenMinQuery =  "SELECT * FROM appointment WHERE start BETWEEN '" + localDateTime + "' AND '" + localDateTime1 + "' AND " +
                    "contact='" + user + "'";
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
        // Testing
        for(Appointment item : apt15Minutes) {
            try {
                ResultSet fifteenMinResultSet = stmt.executeQuery("SELECT * FROM customer WHERE customerId ='" + item.getaCustID() + "'");
                while (fifteenMinResultSet.next()){
                    if(fifteenMinResultSet.getInt("customerId") == item.getaCustID()){
                        item.setaCustName(fifteenMinResultSet.getString("customerName"));
                    }
                }
            } catch(SQLException ex){
                System.out.println("15 min sql exception: " + ex.getMessage());
            }
        }
        // End Testing
        return apt15Minutes;
    }




    // Functions for CRUD interactions for the modifying or adding of appointmens to go below here.


}
