package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import utils.Log;
import dao.*;

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
    static model.User currUser;


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
        boolean loginSuccess = dbUser.userLogin(userName, password);
        if(loginSuccess){
            currUser = dbUser.getCurrentUser();
            System.out.println("Login Success!! username: " + currUser.getUserName() + ". Password: " + currUser.getUserPassword() + ".");
            Log.writeLog(currUser.getUserID(),currUser.getUserName(),loginSuccess);
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            Log.writeLog(000, "Invalid Login", false);
            loginAlert.setContentText("Invalid Username or Password. Please try again.");
            loginAlert.show();
        }

    }
}
