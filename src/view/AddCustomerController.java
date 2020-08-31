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

    // function to clear the comboCities list
    public void clearList(){
        comboCities.clear();
    }

    // Function to check and see that all forms have the proper data in them before attempting to send data to the DB
    public Boolean checkEmptyFields(){
        if (zipTextField.getText() == null || zipTextField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The zip code field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The name field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (addressTextField.getText() == null || addressTextField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The address field cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (cityComboBox.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The location selection box cannot be empty.");
            a.showAndWait();
            return true;
        }
        if (phoneTextField.getText() == null || phoneTextField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("The phone selection box cannot be empty.");
            a.showAndWait();
            return true;
        }
        return false;
    }

    // Add customer to DB
    public void addCustomer(){
        if (checkEmptyFields()){
            return;
        }
        dao.dbCustomer.saveCustomer(nameTextField.getText(),addressTextField.getText(),
                cityComboBox.getSelectionModel().getSelectedIndex()+1, zipTextField.getText(), phoneTextField.getText());
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Entry Updated");
        a.setHeaderText("Entry Modified");
        a.setContentText("Customer Record Added");
        a.showAndWait();
        nameTextField.setText("");
        addressTextField.setText("");
        zipTextField.setText("");
        phoneTextField.setText("");
        comboCities = dao.dbCustomer.getAllCities();
        cityComboBox.setItems(comboCities);
        cityComboBox.getSelectionModel().clearSelection();
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
