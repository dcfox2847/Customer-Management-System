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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @FXML private DatePicker appointmentDatePicker;

    // CLASS VARIABLES
    public static Appointment modifyAppointment;
    private ObservableList<String> comboCities = FXCollections.observableArrayList();

    // Class Constructor
    public ModifyAppointmentController() { }

    // Class Methods and Functions

    // This is a function built purely for testing the values going into and out of the Appointment object
    public void testObject(){
        System.out.println("ID: " + modifyAppointment.getaID());
        System.out.println("Date: " + modifyAppointment.getaStartTime());
        LocalDate date = modifyAppointment.getDateOnly();
        String textDate = date.toString();
        System.out.println("Formatted from SQL: " + textDate);
        appointmentDatePicker.setValue(LocalDate.parse(textDate));
    }

    // Function to modify the record
    public void modifyButtonClicked(javafx.event.ActionEvent actionEvent){
        if(dao.dbAppointment.modifyAppointment(Integer.parseInt(idTextField.getText()), typeTextField.getText(), contactTextField.getText(),
                cityComboBox.getSelectionModel().getSelectedItem(), String.valueOf(appointmentDatePicker.getValue()), timeTextField.getText())){
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
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error with Sql");
            a.setHeaderText("Error with record");
            a.setContentText("Please retry your entry.");
            a.showAndWait();
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
        // Set the date of the DatePicker object
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime testTime = LocalDateTime.parse(timeString, formatter)
;//        LocalDateTime dbTime = dbAppointment.changeToUtc(timeString);
        LocalDateTime utcTime = dbAppointment.changeFromUtc(testTime, timeZone);
        String testTime2 = String.valueOf(utcTime);
        String finalTime = testTime2.substring(11) + ":00";
        timeTextField.setText(finalTime);
    }
}
