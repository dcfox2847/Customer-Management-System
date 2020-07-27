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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    // Class Variables
    private ObservableList<String> comboCities = FXCollections.observableArrayList();

    // Class Controls

    @FXML private Label testLabel;
    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private ComboBox<String> cityComboBox;
    @FXML private TextField zipTextField;
    @FXML private TextField phoneTextField;
    @FXML private Button saveButton;
    @FXML private Button backButton;

    // Class Constructor
    public AddCustomerController() {}

    // Class functions and methods

    public void clearList(){
        comboCities.clear();
    }

    // Add customer to DB
    public void addCustomer(){
        dao.dbCustomer.saveCustomer(nameTextField.getText(),addressTextField.getText(),
                cityComboBox.getSelectionModel().getSelectedIndex()+1, zipTextField.getText(), phoneTextField.getText());
    }

    // Return to the ModifyCustomer page
    public void switchMainView(javafx.event.ActionEvent actionEvent){
        clearList();
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

    // Initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboCities = dao.dbCustomer.getAllCities();
        cityComboBox.setItems(comboCities);
    }
}
