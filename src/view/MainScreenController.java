package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainScreenController implements Initializable {

    // Form Controls
    @FXML private Button reloadButton;
    @FXML private Button reportsButton;
    @FXML private Button aptButton;
    @FXML private Button custButton;
    @FXML private Button deleteButton;
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
//                System.out.println("Appointment: " + item.getaID() + " Start time: " + item.getaStartTime());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime startTime = LocalDateTime.parse(item.getaStartTime(), dtf);
                String timeZone;
                String cityLocation = item.getaLocation();
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
                String newLocalTime = String.valueOf(dbAppointment.changeFromUtc(startTime, timeZone));
                String localTimeFixed = newLocalTime.replace('T', ' ') + ":00";
                item.setaStartTime(localTimeFixed);
                // Show ALERT
                alert15Minutes.setTitle("Upcoming Appointment");
                alert15Minutes.setHeaderText("Upcoming Appointment");
                alert15Minutes.setContentText("Upcoming appointment for " + item.getaDesc() + " with " + item.getaContact() +
                        " starting at " + item.getaStartTime() + " local time. Customer is located in the " + timeZone + " time zone.");
                alert15Minutes.show();
            }

        }
        appointmentMonth = dao.dbAppointment.getMonthlyApt(LoginController.currUser.getUserID());
        // Get time zone by city
        assert appointmentMonth != null;
        for(Appointment apt : appointmentMonth){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(apt.getaStartTime(), dtf);
            String timeZone;
            String cityLocation = apt.getaLocation();
            if (cityLocation.compareTo("New York") == 0 || cityLocation.compareTo("Pickerington") ==0){
                timeZone = "America/New_York";
            }
            else if (cityLocation.compareTo("Los Angeles") == 0){
                timeZone = "America/Los_Angeles";
            }
            else if (cityLocation.compareTo("Toronto") == 0){
                timeZone = "America/Toronto";
            }
            else if (cityLocation.compareTo("Vancouver") == 0){
                timeZone = "America/Vancouver";
            }
            else if (cityLocation.compareTo("Oslo") == 0){
                timeZone = "Europe/Oslo";
            }
            else {
                timeZone = "ETC/UTC";
                System.out.println("Else conditional..... UTC");
            }
            String newLocalTime = String.valueOf(dbAppointment.changeFromUtc(startTime, timeZone));
            String localTimeFixed = newLocalTime.replace('T', ' ') + ":00";
            apt.setaStartTime(localTimeFixed);
        }
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("aCustID"));
        aptCustNameColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aCustName"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aDesc"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aLocation"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("aStartTime"));
//        aptEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aEndTime"));
        aptTableView.setItems(appointmentMonth);
    }

    // function to change to the reports Screen
    public void switchReportsView(javafx.event.ActionEvent actionEvent){
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // function to change to a weekly view. Works based on radio button selection and its listener.
    public void changeToWeek(){
        System.out.println("You have clicked the 'weekly' radio button, and displaying for the week.");
        aptTableView.getItems().clear();
        appointmentWeek = dao.dbAppointment.getWeeklyApt(LoginController.currUser.getUserID());
        // Get time zone by city
        assert appointmentWeek != null;
        for(Appointment apt : appointmentWeek){
            System.out.println("Appointment: " + apt.getaID() + " Start time: " + apt.getaStartTime());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(apt.getaStartTime(), dtf);
            String timeZone;
            String cityLocation = apt.getaLocation();
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
            String newLocalTime = String.valueOf(dbAppointment.changeFromUtc(startTime, timeZone));
            String localTimeFixed = newLocalTime.replace('T', ' ') + ":00";
            apt.setaStartTime(localTimeFixed);
        }
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("aCustID"));
        aptCustNameColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aCustName"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aDesc"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aLocation"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("aStartTime"));
//        aptEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aEndTime"));
        aptTableView.setItems(appointmentWeek);
    }

    // function to change to a month view. Works based on radio button selection and its listener
    public void changeToMonth(){
        System.out.println("You have clicked the 'monthly' radio button, and displaying for the month.");
        aptTableView.getItems().clear();
        appointmentMonth = dao.dbAppointment.getMonthlyApt(LoginController.currUser.getUserID());
        // Get time zone by city
        assert appointmentMonth != null;
        for(Appointment apt : appointmentMonth){
            System.out.println("Appointment: " + apt.getaID() + " Start time: " + apt.getaStartTime());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(apt.getaStartTime(), dtf);
            String timeZone;
            String cityLocation = apt.getaLocation();
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
            String newLocalTime = String.valueOf(dbAppointment.changeFromUtc(startTime, timeZone));
            String localTimeFixed = newLocalTime.replace('T', ' ') + ":00";
            apt.setaStartTime(localTimeFixed);
        }
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("aCustID"));
        aptCustNameColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aCustName"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aDesc"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aLocation"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("aStartTime"));
//        aptEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("aEndTime"));
        aptTableView.setItems(appointmentMonth);
    }

    // function to clear the tableview
    public void clearTable(){
        aptTableView.getItems().clear();
    }

    // function to switch to the customer details screen
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
    // function to switch to the add appointment view
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

    // function to switch to the modify appointment view
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

    // function to delete an appointment based on what you have selected in the table view
    public void deleteAppointment(javafx.event.ActionEvent actionEvent){
        if(aptTableView.getSelectionModel().getSelectedItem() != null){
            Appointment appointmentToDelete = aptTableView.getSelectionModel().getSelectedItem();
            int appointmentId = appointmentToDelete.getaID();
            System.out.println("Customer ID: " + appointmentId);
            dbAppointment.deleteAppointment(appointmentId);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Item Deleted");
            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.YES);
            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.OK){
                    System.out.println("Button Clicked. Item Deleted.");
                }
            });
            aptTableView.getItems().remove(appointmentToDelete);
        }
    }
    }
