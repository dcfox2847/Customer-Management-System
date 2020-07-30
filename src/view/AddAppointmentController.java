package view;

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
    @FXML private TextField locationField;
    @FXML private TextField timeField;

    // Class Variables
    LocalDate date;
    Date sqlDate;

    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    // Class Functions

    public void clearList(){
        allCustomers.clear();
    }

    public void backButtonView(javafx.event.ActionEvent actionEvent){
        clearList();
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
    public String getDate(){
        date = datePicker.getValue();
        sqlDate = Date.valueOf(date);
        System.out.println("Normal java date: " + date);
        System.out.println("SQL date format: " + sqlDate);
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        String returnDate = dtf.format(sqlDate);
        return returnDate;
    }

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

    public void addButtonClicked(javafx.event.ActionEvent actionEvent){
        getDate();
        Customer cust = customerComboBox.getValue();
        if(getTime(timeField.getText())){
            dao.dbAppointment.addAppointment(cust.getcID(), typeField.getText(), descriptionField.getText(), contactField.getText(), locationField.getText(), getDate(), timeField.getText());
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Time Entry Error");
            a.setHeaderText("Time Format Error.");
            a.setContentText("Please retry your entry.");
            a.showAndWait();
        }

    }

    // Initialize Method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allCustomers = dao.dbCustomer.getAllCustomers();
        customerComboBox.setItems(allCustomers);
    }
}
