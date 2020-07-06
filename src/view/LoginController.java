package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    // Form controls

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label applicationGreeting;

    String userName = "";
    String password = "";
    Alert loginAlert = new Alert(Alert.AlertType.INFORMATION);
    ResourceBundle rb;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Locale locale = Locale.getDefault();
        resources = ResourceBundle.getBundle("languages/lang", locale);
        usernameLabel.setText(resources.getString("usrLbl"));
        passwordLabel.setText(resources.getString("passLbl"));
        loginButton.setText(resources.getString("buttonText"));
        applicationGreeting.setText(resources.getString("appMessage"));
        loginAlert.setTitle(resources.getString("alertTitle"));
        loginAlert.setHeaderText(resources.getString("alertHeader"));
        loginAlert.setContentText(resources.getString("alertMessage"));

    }

    // Form control handling methods

    public void loginButtonClick(javafx.event.ActionEvent actionEvent) {
        userName = usernameField.getText();
        password = passwordField.getText();
        // The below can be moved to a function used for error checking to show the user what is wrong.
        System.out.println("username: " + userName + ". Password: " + password + ".");
        loginAlert.setContentText("User: " + userName + " Pass: " + password);
        loginAlert.show();
    }
}
