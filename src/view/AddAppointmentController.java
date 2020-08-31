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
import model.Customer;

import java.net.URL;
import java.sql.Date;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.FormatFlagsConversionMismatchException;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    // Class Controls
    @FXML private Button addAppointmentButton;
    @FXML private Button backButton;
    @FXML private ComboBox<Customer> customerComboBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField descriptionField;
    @FXML private TextField typeField;
    @FXML private TextField contactField;
    @FXML private ComboBox<String> cityComboBox;
    @FXML private TextField timeField;

    // Class Variables
    LocalDate date;
    Date sqlDate;
    private ObservableList<String> comboCities = FXCollections.observableArrayList();

    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    // Class Functions

    // Clears the list on the page
    public void clearList(){
        allCustomers.clear();
    }

    // Function to go back to the previous screen
    public void backButtonView(javafx.event.ActionEvent actionEvent){
        clearList();
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

    // Function to check and see that all forms have the proper data in them before attempting to send data to the DB
    public Boolean checkEmptyFields(){
        if (descriptionField.getText() == null || descriptionField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The description field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (typeField.getText() == null || typeField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The appointment 'Type' field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (contactField.getText() == null || contactField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The contact field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (timeField.getText() == null || timeField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The appointment time field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (customerComboBox.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The customer selection box cannot be empty.");
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
        if (datePicker.getValue() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The date selection box cannot be empty.");
            a.showAndWait();
            return true;
        }
        return false;
    }

    // Get the date from the DatePicker control
    public String getDate(){
        date = datePicker.getValue();
        sqlDate = Date.valueOf(date);
        System.out.println("Normal java date: " + date);
        System.out.println("SQL date format: " + sqlDate);
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        String returnDate = dtf.format(sqlDate);
        return returnDate;
    }

    // Returns boolean on whether the time was entered properly or not in the time field
    public Boolean getTime(String time){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        try {
            dateFormat.parse(time);
            System.out.println(dateFormat.parse(time));
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("This did not work!!!!");
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Time Format Error.");
            a.setContentText("You have entered an incorrect time format. Please try again.");
            a.showAndWait();
            return false;
        }
    }

    // function to check for errors i.e. scheduling outside of business hours, blank fields, etc. and add data to the DB
    public void addButtonClicked(javafx.event.ActionEvent actionEvent){
        if(checkEmptyFields()){
            System.out.println("CheckEmptyFields called!");
            return;
        }
        getDate();
        Customer cust = customerComboBox.getValue();
        boolean overlappingAppointment = false;
        boolean outsideHours = false;
        overlappingAppointment = dbAppointment.addOverlappingAppointment(cityComboBox.getValue(), String.valueOf(datePicker.getValue()), timeField.getText());
        outsideHours = dbAppointment.outsideOfBusinessHours(String.valueOf(datePicker.getValue()), timeField.getText());
        System.out.println("Overlapping appt? " + overlappingAppointment);
        System.out.println("Outside business hours? " + outsideHours);
        if(!overlappingAppointment && !outsideHours) {
            if (getTime(timeField.getText())) {
                dao.dbAppointment.addAppointment(cust.getcID(), typeField.getText(), descriptionField.getText(), contactField.getText(), cityComboBox.getSelectionModel().getSelectedItem(),
                        getDate(), timeField.getText());
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Entry added");
                a.setHeaderText("Entry added");
                a.setContentText("Appointment added successfully");
                a.showAndWait();
                typeField.setText("");
                descriptionField.setText("");
                contactField.setText("");
                comboCities = dao.dbCustomer.getAllCities();
                cityComboBox.setItems(comboCities);
                cityComboBox.getSelectionModel().clearSelection();
                timeField.setText("");
                customerComboBox.getSelectionModel().clearSelection();
                datePicker.setValue(null);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Time Entry Error");
                a.setHeaderText("Time Format Error.");
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

    // Initialize Method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allCustomers = dao.dbCustomer.getAllCustomers();
        customerComboBox.setItems(allCustomers);
        comboCities = dao.dbCustomer.getAllCities();
        cityComboBox.setItems(comboCities);
    }
}
