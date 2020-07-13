package view;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import utils.Log;
import dao.*;
import model.Appointment;
import javafx.fxml.FXML;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainScreenController implements Initializable {

    // Form Controls
    @FXML private Button reloadButton;
    @FXML private Button aptButton;
    @FXML private Button custButton;
    @FXML private TableView<Appointment> aptTableView;
    @FXML private TableColumn<Appointment, Integer> aptIdColumn;
    @FXML private TableColumn<Appointment, String> aptCustNameColumn;
    @FXML private TableColumn<Appointment, String> aptDescriptionColumn;
    @FXML private TableColumn<Appointment, String> aptLocationColumn;
    @FXML private TableColumn<Appointment, String> aptStartColumn;
    @FXML private TableColumn<Appointment, String> aptEndColumn;


    // Class Variables
    ObservableList<Appointment> appointmentFifteen = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentMonth = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentWeek = FXCollections.observableArrayList();

    private User currentUser = LoginController.currUser;

    // class constructor
    public MainScreenController() {}


    // Class Initialization
    // TODO: YOU NEED TO ADD THE CUSTOMER DATA (CUSTOMER ID AND CUSTOMER NAME) INTO THE APPOINTMENT TABLE!!!
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Testing to see what the User ID returns: " + LoginController.currUser.getUserID());
        appointmentMonth = dao.dbAppointment.getMonthlyApt(LoginController.currUser.getUserID());
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("aID"));
        aptCustNameColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aCustName"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aDesc"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aLocation"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("aStartTime"));
        aptEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aEndTime"));
//        appointmentMonth.forEach(item ->{
//            aptIdColumn.setCellValueFactory(cellData -> cellData.getValue().aIDProperty().asObject());

//        });
        aptTableView.setItems(appointmentMonth);
    }
}
