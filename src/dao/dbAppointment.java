package dao;

import java.sql.*;
import java.time.*;
import javafx.collections.*;
import dao.dbConnection;
import model.Appointment;

public class dbAppointment {

    public static ObservableList<Appointment> getMonthlyApt(int id) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment apt;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        try{
            String monthlyQuery = "SELECT * FROM appointment WHERE customerId = '" + id + "'AND " + "start >= '" + begin
                    + "' AND start <= '" + end + "'";
            ResultSet monthlyResults = dbConnection.stmt.executeQuery(monthlyQuery);
            while(monthlyResults.next()){
                apt = new Appointment(monthlyResults.getInt("appointmentId"), monthlyResults.getInt("customerId"),
                        monthlyResults.getString("start"), monthlyResults.getString("end"),
                        monthlyResults.getString("title"), monthlyResults.getString("description"),
                        monthlyResults.getString("location"), monthlyResults.getString("contact"));
            }
            System.out.println("Appointments for the month have been returned!");
            return appointments;
        }catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }

}
