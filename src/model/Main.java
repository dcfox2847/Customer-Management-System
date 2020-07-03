package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Launching Login Page");
        Parent root = FXMLLoader.load(getClass().getResource("../view/test.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {

        launch(args);

//        try {
//            DBConnection.makeConnection();
//            launch(args);
//            DBConnection.closeConnection();
//        } catch (SQLException ex) {
//            System.out.println("SQL Error: " + ex.getMessage());
//        } catch (Exception ex) {
//            System.out.println("Error 2: " + ex.getMessage());
//
//        }
    }

}
