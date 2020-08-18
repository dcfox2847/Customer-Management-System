package view;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Appointment;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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

    // This is a function built purely for testing the values going into and out of the Appointment object
    public void testObject(){
        System.out.println("ID: " + modifyAppointment.getaID());
        System.out.println("Date: " + modifyAppointment.getaStartTime());
        LocalDate date = modifyAppointment.getDateOnly();
        String textDate = date.toString();
        System.out.println("Formatted from SQL: " + textDate);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.US);
//        LocalDate formattedTextDate = LocalDate.parse(textDate, dtf);
//        System.out.println("Formatted for Java: " + formattedTextDate);
        appointmentDatePicker.setValue(LocalDate.parse(textDate));

    }


    // Initialize method
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
        locationTextField.setText(modifyAppointment.getaLocation());
        LocalDate date = modifyAppointment.getDateOnly();
        String textDate = date.toString();
        appointmentDatePicker.setValue(LocalDate.parse(textDate));
        timeTextField.setText(modifyAppointment.getTimeOnly());

    }
}
