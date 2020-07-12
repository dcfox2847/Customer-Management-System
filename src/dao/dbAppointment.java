package dao;

import java.sql.*;
import java.time.*;
import java.util.Locale;

import javafx.collections.*;
import dao.dbConnection;
import model.Appointment;

import static dao.dbConnection.stmt;

public class dbAppointment {

    public static ObservableList<Appointment> getMonthlyApt(int id) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment apt;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        try{
            String monthlyQuery = "SELECT * FROM appointment WHERE customerId = '" + id + "'AND " + "start >= '" + begin
                    + "' AND start <= '" + end + "'";
            ResultSet monthlyResults = stmt.executeQuery(monthlyQuery);
            while(monthlyResults.next()){
                apt = new Appointment(monthlyResults.getInt("appointmentId"), monthlyResults.getInt("customerId"),
                        monthlyResults.getString("start"), monthlyResults.getString("end"),
                        monthlyResults.getString("title"), monthlyResults.getString("description"),
                        monthlyResults.getString("location"), monthlyResults.getString("contact"));
            }
            System.out.println("Appointments for the month have been returned!");
            stmt.close();
            return appointments;
        }catch (SQLException ex){
            System.out.println("SQLException: (monthly query)" + ex.getMessage());
            return null;
        }
    }

    public static ObservableList<Appointment> getWeeklyApt(int id){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment apt;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusWeeks(1);
        try{
            String weeklyQuery = "SELECT * from appointment WHERE customerId = '" + id + "'AND " + "start >= '" + begin + "' AND start <= '" + end + "'";
            ResultSet weeklyResults = stmt.executeQuery(weeklyQuery);
            while(weeklyResults.next()) {
                apt = new Appointment(weeklyResults.getInt("appointmentId"), weeklyResults.getInt("customerId"),
                        weeklyResults.getString("start"), weeklyResults.getString("end"),
                        weeklyResults.getString("title"), weeklyResults.getString("description"),
                        weeklyResults.getString("location"), weeklyResults.getString("contact"));
                appointments.add(apt);
            }
            System.out.println("Appointments for the week have been returned!");
            stmt.close();
            return appointments;
        }catch (SQLException ex) {
            System.out.println("SQLException (weekly query): " + ex.getMessage());
            return null;
        }
    }



}
