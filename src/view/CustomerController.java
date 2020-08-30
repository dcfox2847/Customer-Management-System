package view;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    // Class variables
    public Customer customer;

    // FXML controls
    @FXML private Button custBackButton;
    @FXML private Button modifyButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
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

    // TEST FUNCTION:
    // This function takes the selection from the table that you have, and retrieves the information from the selected row.
    // FIND A WAY TO PASS THIS DATA TO THE NEXT SCENE!!!
    public void getCustomerFromTable(javafx.event.ActionEvent actionEvent) {
        customer = custTableView.getSelectionModel().getSelectedItem();
        ModifyCustomerController.modifyCustomer = customer;
        System.out.println(customer.toString());
        System.out.println("Customer name: " + customer.getcName());
        System.out.println("Customer ID: " + customer.getcID());
        System.out.println("Customer address: " + customer.getcAddress());
        custTableView.getItems().clear();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root;
        Scene modifyCustomerScene = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyCustomer.fxml"));
            root = loader.load();
            modifyCustomerScene = new Scene(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.setScene(modifyCustomerScene);
        stage.show();
    }


    // Used for the back button
    public void switchMainView(javafx.event.ActionEvent actionEvent){
        custTableView.getItems().clear();
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

    // Switch to the ADD CUSTOMER screen
    public void switchAddCustomerView(javafx.event.ActionEvent actionEvent){
        custTableView.getItems().clear();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Functions to handle customer delete.

    public void onEdit(javafx.event.ActionEvent actionEvent){
        if(custTableView.getSelectionModel().getSelectedItem() != null){
            Customer customerToDelete = custTableView.getSelectionModel().getSelectedItem();
            int customerId = customerToDelete.getcID();
            System.out.println("Customer ID: " + customerId);
            dbCustomer.deleteCustomer(customerId);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Item Deleted");
            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.YES);
            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.OK){
                    System.out.println("Button Clicked. Item Deleted.");
                }
            });
            custTableView.getItems().remove(customerToDelete);
        }
    }

    // Test to try passing customer data

}
