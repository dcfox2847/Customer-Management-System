package view;



import dao.dbCustomer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static dao.dbReports.*;


public class ReportsController implements Initializable {

    // FXML form controls
    @FXML private Button clearButton;
    @FXML private Button backButton;
    @FXML private TextArea reportTextArea;
    @FXML private RadioButton appointmentMonthRadioButton;
    @FXML private RadioButton consultantScheduleRadioButton;
    @FXML private RadioButton appointmentCustomerRadioButton;

    // Class variables
    ToggleGroup radioGroup = new ToggleGroup();

    // Class functions and methods

    // function for generate button
    public void clearTextArea(){
        reportTextArea.clear();
        if (appointmentMonthRadioButton.isSelected()) {
            appointmentMonthRadioButton.setSelected(false);
        }
        else if(consultantScheduleRadioButton.isSelected()){
            consultantScheduleRadioButton.setSelected(false);
        } else {
            appointmentCustomerRadioButton.setSelected(false);
        }
    }

    // function for the back button
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

    // Initialize method
    public void initialize(URL location, ResourceBundle resources) {
        appointmentMonthRadioButton.setToggleGroup(radioGroup);
        appointmentCustomerRadioButton.setToggleGroup(radioGroup);
        consultantScheduleRadioButton.setToggleGroup(radioGroup);
        radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(appointmentMonthRadioButton.isSelected()){
                    appointmentTypeByMonth(reportTextArea);
                }
                else if(appointmentCustomerRadioButton.isSelected()){
                    totalCustomerAppointments(reportTextArea);
                }
                else if(consultantScheduleRadioButton.isSelected()){
                    consultantSchedule(reportTextArea);
                }
            }
        });
    }
}
