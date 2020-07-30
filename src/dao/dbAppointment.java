package dao;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    // Class Functions and methods

    // TODO: Finish this conversion function
    // Function to create a SQL DB readable time stamp
    public static String convertTimeStamp(String date, String time, String location, boolean startMode){
        return null;
    }

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

    public static ObservableList<Appointment> get15MinuteApt(int id){
        ObservableList<Appointment> apt15Minutes = FXCollections.observableArrayList();
        Appointment apt;
        Alert alert15Minutes = new Alert(Alert.AlertType.INFORMATION);
        alert15Minutes.setTitle("Upcoming Appointment");
        alert15Minutes.setHeaderText("Upcoming Appointment");
        LocalDateTime now = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
        LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime localDateTime1 = localDateTime.plusMinutes(15);
        user = dbUser.getCurrentUser().getUserName();
        try{
            String fifteenMinQuery =  "SELECT * FROM appointment WHERE userID = '" + id + "' AND start BETWEEN '" + localDateTime + "' AND '" + localDateTime1 + "' AND " +
                    "contact='" + user + "'";
            ResultSet resultSet = stmt.executeQuery(fifteenMinQuery);
            if(resultSet.next()){
                apt = new Appointment(resultSet.getInt("appointmentId"), resultSet.getInt("customerId"),
                        resultSet.getString("start"), resultSet.getString("end"), resultSet.getString("title"),
                        resultSet.getString("description"), resultSet.getString("location"), resultSet.getString("contact"));
                apt15Minutes.add(apt);
                alert15Minutes.setContentText("You have an appointment with " + apt.getaCustName() + " at " + apt.getaStartTime());
                alert15Minutes.show();
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
        LocalDateTime zuluTime = time.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatZulu = zuluTime.format(dtf);
        LocalDateTime convertedZulu = LocalDateTime.parse(formatZulu, dtf);
        System.out.println("Final formatted UTC time for the DB: " + convertedZulu);
        return zuluTime;
    }


    // TODO: MAKE THE FUNCTION BELOW TO SAVE DATA INTO THE DATABASE.
    // Function to create a new appointment entry in the database
    // Arguments to replace -> (  int id, String type, String contact, String location, String date, String time  )
    public static boolean addAppointment(int id, String type, String contact, String location, String date, String time){
        String preConvertedString = date + " " + time;
        LocalDateTime dateTimeString = changeToUtc(preConvertedString);
        try{
            String query = "INSERT INTO appointment SET customerId='" + id + "', userId='" + LoginController.currUser.getUserID() + "', title='" + type +
                    "', description='" + type + "', location='" + location + "', contact='" + contact + "', type='" + type + "', url='', start='" + preConvertedString +
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


    // Functions for CRUD interactions for the modifying or adding of appointmens to go below here.


}
