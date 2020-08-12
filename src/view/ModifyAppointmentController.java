package view;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Appointment;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    @FXML private TextField locationTextField;
    @FXML private TextField timeTextField;
    @FXML private DatePicker appointmentDatePicker;

    // CLASS VARIABLES
    public static Appointment modifyAppointment;

    // Class Constructor
    public ModifyAppointmentController() { }

    // Class Methods and Functions
    public void testObject(){
        System.out.println("ID: " + modifyAppointment.getaID());
        System.out.println("Date: " + modifyAppointment.getaStartTime());
        LocalDate date = modifyAppointment.getDateOnly();
        String textDate = date.toString();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    }


    // Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
