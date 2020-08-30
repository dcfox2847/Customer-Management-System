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
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ModifyCustomerController implements Initializable {

    // FXML Controls
   @FXML private Label testLabel;
   @FXML private TextField idTextField;
   @FXML private TextField nameTextField;
   @FXML private TextField addressTextField;
   @FXML private ComboBox<String> cityComboBox;
   @FXML private TextField zipTextField;
   @FXML private TextField phoneTextField;
   @FXML private Button saveButton;
   @FXML private Button backButton;

   // Class variables
    public static Customer modifyCustomer;
    private ObservableList<String> comboCities = FXCollections.observableArrayList();

    // class constructor
    public ModifyCustomerController(){}

    //Class Functions and Methods

    public void clearList(){
        comboCities.clear();
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

    // Update the customer information using textfield information
    public void updateCustomer(){
        dao.dbCustomer.updateCustomer(Integer.parseInt(idTextField.getText()), nameTextField.getText(), addressTextField.getText(), cityComboBox.getSelectionModel().getSelectedIndex()+1,
                zipTextField.getText(), phoneTextField.getText());
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Entry Updated");
        a.setHeaderText("Entry Modified");
        a.setContentText("Customer Record Updated");
        a.showAndWait();
        nameTextField.setText("");
        addressTextField.setText("");
        zipTextField.setText("");
        phoneTextField.setText("");
        comboCities = dao.dbCustomer.getAllCities();
        cityComboBox.setItems(comboCities);
        cityComboBox.getSelectionModel().clearSelection();
    }


    // Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Customer city: " + modifyCustomer.getcCity() + " Customer cityID: " + modifyCustomer.getcCityID());
        idTextField.setText(Integer.toString(modifyCustomer.getcID()));
        idTextField.setDisable(true);
        nameTextField.setText(modifyCustomer.getcName());
        addressTextField.setText(modifyCustomer.getcAddress());
        String cityLocation = modifyCustomer.getcCity();
        comboCities = dao.dbCustomer.getAllCities();
        cityComboBox.setItems(comboCities);
        for (String city : comboCities){
            if (city.compareTo(cityLocation) == 0){
                cityComboBox.setValue(city);
            }
        }
        zipTextField.setText(modifyCustomer.getcZip());
        phoneTextField.setText(modifyCustomer.getcPhone());
    }

}
