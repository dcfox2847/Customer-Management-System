package view;

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
   @FXML private TextField cityIdTextField;
   @FXML private TextField cityTextField;
   @FXML private TextField zipTextField;
   @FXML private TextField phoneTextField;
   @FXML private Button saveButton;
   @FXML private Button backButton;

   // Class variables
    public static Customer modifyCustomer;

    // class constructor
    public ModifyCustomerController(){}

    //Class Functions and Methods

    // Return to the ModifyCustomer page
    public void switchMainView(javafx.event.ActionEvent actionEvent){
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
        dao.dbCustomer.updateCustomer(Integer.parseInt(idTextField.getText()), nameTextField.getText(), addressTextField.getText(), Integer.parseInt(cityIdTextField.getText()),
                zipTextField.getText(), phoneTextField.getText());
    }



    // Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Customer city: " + modifyCustomer.getcCity() + " Customer cityID: " + modifyCustomer.getcCityID());
        idTextField.setText(Integer.toString(modifyCustomer.getcID()));
        idTextField.setDisable(true);
        nameTextField.setText(modifyCustomer.getcName());
        addressTextField.setText(modifyCustomer.getcAddress());
        cityIdTextField.setText(Integer.toString(modifyCustomer.getcCityID()));
        cityTextField.setText(modifyCustomer.getcCity());
        zipTextField.setText(modifyCustomer.getcZip());
        phoneTextField.setText(modifyCustomer.getcPhone());
    }

    // TESTING
    // Class functions and methods
//    public void testDisplayData(){
//        testLabel.setText("The Customers name is: " + modifyCustomer.getcName());
//    }

}
