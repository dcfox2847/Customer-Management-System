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
    private Customer modifyCustomer;

    // Getter and Setters for the customer variable
    public Customer getModifyCustomer() {
        return modifyCustomer;
    }

    public void setModifyCustomer(Customer modifyCustomer) {
        this.modifyCustomer = modifyCustomer;
    }

    // class constructor
    public ModifyCustomerController(){}

    public ModifyCustomerController(Customer modifyCustomer) {
        this.modifyCustomer = modifyCustomer;
    }

    // Class functions and methods
    public void testDisplayData(){
        testLabel.setText("The Customers name is: " + modifyCustomer.getcName());
    }

    public void changeText(){
        testDisplayData();
        System.out.println(modifyCustomer.toString());
        System.out.println(modifyCustomer.getcName());
    }


    // Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println("From in the MODIFY CONTROLLER: " + modifyCustomer.getcName());
//        testDisplayData();
    }
}
