package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import utils.Log;
import dao.*;
import model.Appointment;
import javafx.fxml.FXML;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @FXML private RadioButton aptWeeklyRadio = new RadioButton("aptWeeklyRadio");
    @FXML private RadioButton aptMonthlyRadio = new RadioButton("aptMonthlyRadio");



    // Class Variables
    ObservableList<Appointment> appointmentFifteen = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentMonth = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentWeek = FXCollections.observableArrayList();

    public Appointment appointment;

    private User currentUser = LoginController.currUser;
    Alert alert15Minutes = new Alert(Alert.AlertType.INFORMATION);

    // Toggle Group and related functions
    ToggleGroup radioGroup = new ToggleGroup();

    // class constructor
    public MainScreenController() {}


    // Class Initialization

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setting up of the radio buttons
        aptMonthlyRadio.setToggleGroup(radioGroup);
        aptWeeklyRadio.setToggleGroup(radioGroup);
        aptMonthlyRadio.setSelected(true);
        radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(aptWeeklyRadio.isSelected()){
                    changeToWeek();
                }
                else if(aptMonthlyRadio.isSelected()){
                    changeToMonth();
                }
            }
        });

        appointmentFifteen = dao.dbAppointment.get15MinuteApt(LoginController.currUser.getUserID());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println("Current date and time " + formatter.format(date));
        System.out.println("Appointments in 15 minutes: " + appointmentFifteen.size());
        if(appointmentFifteen.size() != 0){
            for(Appointment item : appointmentFifteen){
                alert15Minutes.setTitle("Upcoming Appointment");
                alert15Minutes.setHeaderText("Upcoming Appointment");
                alert15Minutes.setContentText("Upcoming appointment with " + item.getaCustName() + " starting at " +
                        item.getaStartTime() + ".");
                alert15Minutes.show();
            }

        }
        System.out.println("Testing to see what the User ID returns: " + LoginController.currUser.getUserID());
        appointmentMonth = dao.dbAppointment.getMonthlyApt(LoginController.currUser.getUserID());
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("aCustID"));
        aptCustNameColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aCustName"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aDesc"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aLocation"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("aStartTime"));
        aptEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aEndTime"));
        aptTableView.setItems(appointmentMonth);
    }

    public void changeToWeek(){
        System.out.println("You have clicked the 'weekly' radio button, and displaying for the week.");
        aptTableView.getItems().clear();
        appointmentWeek = dao.dbAppointment.getWeeklyApt(LoginController.currUser.getUserID());
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("aCustID"));
        aptCustNameColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aCustName"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aDesc"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aLocation"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("aStartTime"));
        aptEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aEndTime"));
        aptTableView.setItems(appointmentWeek);
    }

    public void changeToMonth(){
        System.out.println("You have clicked the 'monthly' radio button, and displaying for the month.");
        aptTableView.getItems().clear();
        appointmentMonth = dao.dbAppointment.getMonthlyApt(LoginController.currUser.getUserID());
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("aCustID"));
        aptCustNameColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aCustName"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aDesc"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aLocation"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("aStartTime"));
        aptEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aEndTime"));
        aptTableView.setItems(appointmentMonth);
    }

    public void clearTable(){
        aptTableView.getItems().clear();
    }

    public void switchCustomerView(javafx.event.ActionEvent actionEvent){
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchAddAppointmentView(javafx.event.ActionEvent actionEvent){
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchModifyAppointmentView(javafx.event.ActionEvent actionEvent){
        appointment = aptTableView.getSelectionModel().getSelectedItem();
        ModifyAppointmentController.modifyAppointment = appointment;
        System.out.println(appointment.toString());
        aptTableView.getItems().clear();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root;
        Scene modifyAppointmentScene = null;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyAppointment.fxml"));
            root = loader.load();
            modifyAppointmentScene = new Scene(root);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        stage.setScene(modifyAppointmentScene);
        stage.show();
    }

}
