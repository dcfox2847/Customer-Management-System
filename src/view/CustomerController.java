package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.User;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    // FXML controls
    @FXML private Button custBackButton;
    @FXML private Button modifyButton;
    @FXML private Button addButton;
    @FXML private TableView<Customer> custTableView;
    @FXML private TableColumn<Customer, Integer> custIDColumn;
    @FXML private TableColumn<Customer, String> custNameColumn;
    @FXML private TableColumn<Customer, String> custAddressColumn;
    @FXML private TableColumn<Customer, String> custPhoneColumn;
    @FXML private TableColumn<Customer, String> custZipColumn;
    @FXML private TableColumn<Customer, String> custCityColumn;

    // Class Variables
    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    // Class Constructor
    public CustomerController() { }


    // Class Methods
    // Initialize the scene
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allCustomers = dao.dbCustomer.getAllCustomers();
        custIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("cID"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("cName"));
        custAddressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("cAddress"));
        custPhoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("cPhone"));
        custZipColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("cZip"));
        custCityColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("cCity"));
        custTableView.setItems(allCustomers);
    }

    // Used for the back button
    public void switchMainView(javafx.event.ActionEvent actionEvent){
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


}
