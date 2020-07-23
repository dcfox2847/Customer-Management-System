package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;


public class ModifyCustomerController implements Initializable {

    // FXML Controls
   @FXML private Label testLabel;

   // Class variables
    public static Customer modifyCustomer;

    // class constructor
    public ModifyCustomerController(){}

    // Class functions and methods
    public void testDisplayData(){
        testLabel.setText("The Customers name is: " + modifyCustomer.getcName());
    }

    // Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("From in the MODIFY CONTROLLER: " + modifyCustomer.getcName());
        testDisplayData();
    }
}
