/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingappc195hoseagibsonv2;

import DBManager.ConnectorData;
import ViewController.LoginScreenController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author hosea
 */
public class SchedulingAppC195HoseaGibsonV2 extends Application{

    @Override
    public void start(Stage stage) throws ClassNotFoundException, SQLException, IOException, Exception {
        FXMLLoader loader = new FXMLLoader(LoginScreenController.class.getResource("LoginScreen.fxml"));
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setTitle("Scheduling App - Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, Exception {
        ConnectorData.openConnection();
        launch(args);
        ConnectorData.closeConnection();
    }
    
}
