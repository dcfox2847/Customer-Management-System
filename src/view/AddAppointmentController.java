package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Date;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddAppointmentController {

    // Class Controls
    @FXML private Button addAppointmentButton;
    @FXML private Button backButton;
    @FXML private DatePicker datePicker;
    @FXML private TextField typeField;
    @FXML private TextField contactField;
    @FXML private TextField locationField;
    @FXML private TextField timeField;

    // Class Variables
    LocalDate date;
    Date sqlDate;

    // Class Functions

    public void backButtonView(javafx.event.ActionEvent actionEvent){
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

    // Get the date from the DatePicker control
    public Date getDate(){
        date = datePicker.getValue();
        sqlDate = Date.valueOf(date);
        System.out.println("Normal java date: " + date);
        System.out.println("SQL date format: " + sqlDate);
        return sqlDate;
    }

    public void addButtonClicked(javafx.event.ActionEvent actionEvent){
        getDate();
        dao.dbAppointment.addAppointment(1, "Meeting", "contact", "loc", "2020-07-30", "01:00:00");
    }

}
