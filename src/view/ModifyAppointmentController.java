package view;

import dao.dbAppointment;
import dao.dbCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {

    // FXML Controls
//    @FXML private Label titleLabel;
    @FXML private Button backButton;
    @FXML private Button modifyButton;
    @FXML private TextField idTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField contactTextField;
    @FXML private ComboBox<String> cityComboBox;
    @FXML private TextField timeTextField;
    @FXML private TextField custTimeTextField;
    @FXML private TextField endTimeTextField;
    @FXML private TextField custEndTimeTextField;
    @FXML private TextField durationTextField;
    @FXML private DatePicker appointmentDatePicker;

    // CLASS VARIABLES
    public static Appointment modifyAppointment;
    private ObservableList<String> comboCities = FXCollections.observableArrayList();

    // Class Constructor
    public ModifyAppointmentController() { }

    // Class Methods and Functions

    // Function to make sure that the appointment time isn't show as negative
    public boolean checkDuration(){
        String timeString = timeTextField.getText();
        String endTimeString = endTimeTextField.getText();
        LocalTime start = LocalTime.parse(timeString);
        LocalTime end = LocalTime.parse(endTimeString);
        if(end.isBefore(start)){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The appointment duration is a negative number. Please check that the appointment end time is after the start time.");
            a.showAndWait();
            return true;
        }
        return false;
    }

    // Function to check and see that all forms have the proper data in them before attempting to send data to the DB
    public Boolean checkEmptyFields(){
        if (typeTextField.getText() == null || typeTextField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The appointment 'Type' field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (contactTextField.getText() == null || contactTextField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The contact field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (timeTextField.getText() == null || timeTextField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The appointment start time field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (endTimeTextField.getText() == null || endTimeTextField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The appointment end time field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (durationTextField.getText() == null || durationTextField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The duration time field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (cityComboBox.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The location selection box cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (appointmentDatePicker.getValue() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The date selection box cannot be empty.");
            a.showAndWait();
            return true;
        }
        return false;
    }

    // Function to modify the record
    public void modifyButtonClicked(javafx.event.ActionEvent actionEvent){
        if(checkEmptyFields()){
            return;
        }
        if(checkDuration()){
            return;
        }
        boolean overlappingAppointment = false;
        boolean outsideHours = false;
        overlappingAppointment = dbAppointment.overlappingAppointment(Integer.parseInt(idTextField.getText()), cityComboBox.getValue(),
                String.valueOf(appointmentDatePicker.getValue()), timeTextField.getText());
        outsideHours = dbAppointment.outsideOfBusinessHours(String.valueOf(appointmentDatePicker.getValue()), timeTextField.getText());
        System.out.println("Overlapping appt? " + overlappingAppointment);
        System.out.println("Outside business hours? " + outsideHours);
        // Use functions to test for
        if (!overlappingAppointment && !outsideHours) {
            if (dao.dbAppointment.modifyAppointment(Integer.parseInt(idTextField.getText()), typeTextField.getText(), contactTextField.getText(),
                    cityComboBox.getSelectionModel().getSelectedItem(), String.valueOf(appointmentDatePicker.getValue()), timeTextField.getText(), endTimeTextField.getText())){
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Entry added");
                a.setHeaderText("Entry added");
                a.setContentText("Appointment added successfully");
                a.showAndWait();
                typeTextField.setText("");
                contactTextField.setText("");
                comboCities = dao.dbCustomer.getAllCities();
                cityComboBox.setItems(comboCities);
                cityComboBox.getSelectionModel().clearSelection();
                appointmentDatePicker.setValue(null);
                timeTextField.setText("");
                endTimeTextField.setText("");
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error with Sql");
                a.setHeaderText("Error with record");
                a.setContentText("Please retry your entry.");
                a.showAndWait();
            }
        } else {
            if ( overlappingAppointment && outsideHours){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error Encountered");
                a.setContentText("The appointment you are trying to modify is both outside of normal " +
                        "business operating hours, and overlaps with an existing appointment at this location.");
                a.showAndWait();
            } else if (overlappingAppointment){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error Encountered");
                a.setContentText(" The appointment you are trying to schedule overlaps with another " +
                        "appointment at this time and location.");
                a.showAndWait();
            } else if (outsideHours){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error Encountered");
                a.setContentText("The appointment you are modifying is set outside of normal business hours.");
                a.showAndWait();
            }
        }
    }

    public void backButtonClicked(javafx.event.ActionEvent actionEvent){
        dbCustomer.allCities.clear();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    //-------------------------------------------------------------------------------------------------
    // Initialize method
    //-------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appointmentDatePicker.setConverter(
                new StringConverter<LocalDate>() {

                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    @Override
                    public String toString(LocalDate object) {
                        return (object != null) ? dateTimeFormatter.format(object) : "";
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        return (string != null && !string.isEmpty())
                                ? LocalDate.parse(string, dateTimeFormatter)
                        : null;
                    }
                }
        );
        idTextField.setText(Integer.toString(modifyAppointment.getaID()));
        typeTextField.setText(modifyAppointment.getaTitle());
        contactTextField.setText(modifyAppointment.getaContact());
        // set a variable for the location, find the matching city, and set the combo box data
        String cityLocation = modifyAppointment.getaLocation();
        String timeZone;
        comboCities = dao.dbCustomer.getAllCities();
        cityComboBox.setItems(comboCities);
        for (String city : comboCities){
            if (city.compareTo(cityLocation) == 0){
                cityComboBox.setValue(city);
            }
        }
        // Finish filling out fields
        // Set the date of the DatePicker object and disable weekends

        LocalDate date = modifyAppointment.getDateOnly();
        String textDate = date.toString();
        appointmentDatePicker.setValue(LocalDate.parse(textDate));

        // Make conditional to set value for the city's time zone!!!! Transfer to ZoneId!!!!
        if (cityLocation.compareTo("New York") == 0 || cityLocation.compareTo("Pickerington") ==0){
            timeZone = "America/New_York";
            System.out.println("New York timezone");
        }
        else if (cityLocation.compareTo("Los Angeles") == 0){
            timeZone = "America/Los_Angeles";
            System.out.println("LA timezone");
        }
        else if (cityLocation.compareTo("Toronto") == 0){
            timeZone = "America/Toronto";
            System.out.println("Toronto timezone");
        }
        else if (cityLocation.compareTo("Vancouver") == 0){
            timeZone = "America/Vancouver";
            System.out.println("Vancouver timezone");
        }
        else if (cityLocation.compareTo("Oslo") == 0){
            timeZone = "Europe/Oslo";
            System.out.println("Oslo timezone");
        }
        else {
            timeZone = "ETC/UTC";
            System.out.println("Else conditional..... UTC");
        }
        // Parse the time from the local date object to the appropriate time based on the appointments location.
        String timeString = modifyAppointment.getaStartTime();
        String endTimeString = modifyAppointment.getaEndTime();
        ZoneId oldZone = ZoneId.systemDefault();
        ZoneId newZone = ZoneId.of(timeZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Get the time and date for the customer and for the user
        LocalDateTime testTime = LocalDateTime.parse(timeString, formatter);
//        String testerUser = testTime.format(formatter);
        LocalDateTime newZoneTestTime = testTime.atZone(newZone).withZoneSameInstant(oldZone).toLocalDateTime();
        LocalDateTime endTestTime = LocalDateTime.parse(endTimeString, formatter);
        LocalDateTime newZoneEndTestTime = endTestTime.atZone(newZone).withZoneSameInstant(oldZone).toLocalDateTime();
        // Get the formatted string for the user and the customer
        String formattedStringTime = newZoneTestTime.format(formatter);
//        String customerFormattedStringTime = custNewZoneTestTime.format(formatter);
        String endFormattedStringTime = newZoneEndTestTime.format(formatter);
//        String customerEndFormattedStringTime = custNewZoneEndTestTime.format(formatter);
//        LocalDateTime dbTime = dbAppointment.changeToUtc(timeString);
//        LocalDateTime utcTime = dbAppointment.changeFromUtc(testTime, timeZone);
//        String testTime2 = String.valueOf(utcTime);
//        String testerTester = timeString.substring(11);
        String finalTime = formattedStringTime.substring(11);
        // Insert the substring for the customers time here
        String finalEndTime = endFormattedStringTime.substring(11);
        // Insert the substring for the customers end time hereS
        timeTextField.setText(finalTime); // re-use 'finalTime'
        custTimeTextField.setText(timeString.substring(11));
        endTimeTextField.setText(finalEndTime);
        custEndTimeTextField.setText(endTimeString.substring(11));
        durationTextField.setText(modifyAppointment.getaDuration());
    }
}
