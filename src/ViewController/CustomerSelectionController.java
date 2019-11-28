package ViewController;

import DBManager.CustomerData;
import Model.Customer;
import static ViewController.AppointmentCalendarController.isMod;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hosea Gibson
 */
public class CustomerSelectionController implements Initializable {
    
    @FXML
    private ListView<Customer> lvCustomer;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSelect;
    
    @FXML
    public static Customer selectedCust;

    /*
        Method for to exit the program when a button is pressed
        Alertw the user if they want to exit the program 
    */
    @FXML
    void onClickExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Customer Additon");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the screen. \nPress Cancel to stay on this screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                FXMLLoader aCLoader = new FXMLLoader(AppointmentCalendarController.class.getResource("AppointmentCalendar.fxml"));
                Parent aCScreen = aCLoader.load();
                Scene aCScene = new Scene(aCScreen);
                Stage aCStage = new Stage();
                aCStage.setTitle("Appointment Calendar");
                aCStage.setScene(aCScene);
                aCStage.show();
                Stage modCustStage = (Stage) btnExit.getScene().getWindow();
                modCustStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            alert.close();
        }
    }
    
    /*
        Method for customers in a list that can be selected
        this allows the user the select a customer to e modified
        Alerts user if they want to select a customer to be modified
        Or to delete customer
    */
    @FXML
    void onClickSelection(ActionEvent event) {
        if (lvCustomer.getSelectionModel().getSelectedItem() == null) {
            Alert nullAlert = new Alert(AlertType.ERROR);
            nullAlert.setTitle("Customer Modification Error");
            nullAlert.setHeaderText("The customer is not able to be modified!");
            nullAlert.setContentText("There was no customer selected!");
            nullAlert.showAndWait();
        }
        else if (isMod) {
            Alert modAlert = new Alert(AlertType.CONFIRMATION);
            modAlert.setTitle("Modify Customer");
            modAlert.setHeaderText("Are you sure you want to modify this customer?");
            modAlert.setContentText("Press OK to modify the customer. \nPress Cancel to cancel the modification.");
            modAlert.showAndWait();
            if (modAlert.getResult() == ButtonType.OK) {
                try {
                    selectedCust = lvCustomer.getSelectionModel().getSelectedItem();
                    FXMLLoader modCustLoader = new FXMLLoader(ModifyCustomerController.class.getResource("ModifyCustomer.fxml"));
                    Parent modCustScreen = modCustLoader.load();
                    Scene modCustScene = new Scene(modCustScreen);
                    Stage modCustStage = new Stage();
                    modCustStage.setTitle("Modify Customer");
                    modCustStage.setScene(modCustScene);
                    modCustStage.setResizable(false);
                    modCustStage.show();
                    Stage custSelectionStage = (Stage) btnSelect.getScene().getWindow();
                    custSelectionStage.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            Alert delAlert = new Alert(AlertType.CONFIRMATION);
            delAlert.setTitle("Delete Customer");
            delAlert.setHeaderText("Are you sure you want to delete a customer?");
            delAlert.setContentText("Press OK to delete a customer. \nPress Cancel to cancel the deletion.");
            delAlert.showAndWait();
            if (delAlert.getResult() == ButtonType.OK) {
                selectedCust = lvCustomer.getSelectionModel().getSelectedItem();
                try {
                    CustomerData.deleteCustomer(selectedCust);
                    setCustomerList();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void convertCustomerString() {
        lvCustomer.setCellFactory(c -> new ListCell<Customer>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || item.getCustomerName() == null) {
                    setText("");
                }
                else {
                    setText(item.getCustomerName());
                }
            }
        });
    }
    
    public void setCustomerList() {
        lvCustomer.setItems(CustomerData.getActiveCustomers());
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCustomerList();
        convertCustomerString();
    }    
    
}
