package dao;

import javax.xml.soap.Text;
import java.awt.*;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static dao.dbConnection.rs;
import static dao.dbConnection.stmt;

public class dbReports {


    // Class functions and methods

    // function to get appointment type by month
    public static void appointmentTypeByMonth(javafx.scene.control.TextArea textArea){
        try{
            String query = "SELECT description, MONTHNAME(start) as 'MONTH', COUNT(*) as 'TOTAL' FROM appointment" +
                    " GROUP BY description, MONTHNAME(start)";
            ResultSet rs = stmt.executeQuery(query);
            StringBuilder resultLine = new StringBuilder();
            while(rs.next()){
                resultLine.append("Month: " + rs.getString("MONTH") + ". Description: " + rs.getString("description") +
                        ". Total: " + rs.getInt("TOTAL") + "\n");
                textArea.setText(resultLine.toString());
            }
        } catch (SQLException e){
            System.out.println("SQL Exception : " + e.getMessage());
        }
    }

    // function to get appointments for all consultants schedules
    public static void consultantSchedule(javafx.scene.control.TextArea textArea){
        try{
            String query = "SELECT appointment.contact AS contact, appointment.location AS location, appointment.description AS description, user.userName AS userName, customer.customerName AS customerName, start AS start " +
                    "FROM appointment " +
                    "JOIN customer ON customer.customerId = appointment.customerId " +
                    "JOIN user ON appointment.userId = user.userId " +
                    "GROUP BY user.userName, appointment.contact, appointment.location, appointment.description, customer.customerName, start";
            ResultSet rs = stmt.executeQuery(query);
            StringBuilder resultLine = new StringBuilder();
            while(rs.next()){
                // Time testing
                String timeString = rs.getString("start");
                String location = rs.getString("location");
                String timeZone = getTimeZone(location);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime testTime = LocalDateTime.parse(timeString, formatter);
                String formattedStringTime = testTime.format(formatter);
                LocalDateTime utcConverted = dbAppointment.changeToUtc(formattedStringTime);
                LocalDateTime localConverted = dbAppointment.changeFromUtc(utcConverted, timeZone);
                String finalTime = localConverted.format(formatter);
                // Time testing
                resultLine.append("User " + rs.getString("userName") + "'s appointments.\n" +
                        "Appointment Contact: " + rs.getString("contact") + ".\nMeeting description: " +
                        rs.getString("description") + ".\nCustomer Name: " + rs.getString("customerName") +
                        "\nAppointment time and date: " + finalTime + "\n\n");
                textArea.setText(resultLine.toString());
            }
        } catch (SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
//     function to get total appointments by customer
    public static void totalCustomerAppointments (javafx.scene.control.TextArea textArea){
        textArea.appendText("Amount of appointments by Customer:\n\n");
        try{
            String query = "SELECT customer.customerName AS customerName, COUNT(*) AS 'Total' " +
                    "FROM customer JOIN appointment ON customer.customerId = appointment.customerId " +
                    "GROUP BY customerName";
            ResultSet rs = stmt.executeQuery(query);
            StringBuilder resultLine = new StringBuilder();
            while(rs.next()){
                resultLine.append("Customers name: " + rs.getString("customerName") +
                        "\nTotal number of appointments: " + rs.getString("Total") + "\n\n");
                textArea.setText(resultLine.toString());
            }
        } catch (SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    // function to get city timezone
    public static String getTimeZone(String location) {
        if (location.compareTo("New York") == 0 || location.compareTo("Pickerington") == 0) {
            return "America/New_York";
        } else if (location.compareTo("Los Angeles") == 0) {
            return "America/Los_Angeles";
        } else if (location.compareTo("Toronto") == 0) {
            return "America/Toronto";
        } else if (location.compareTo("Vancouver") == 0) {
            return "America/Vancouver";
        } else if (location.compareTo("Oslo") == 0) {
            return "Europe/Oslo";
        } else {
            return "UTC";
        }
    }

}
