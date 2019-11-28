package ViewController;

import DBManager.AppointmentData;
import DBManager.CustomerData;
import DBManager.UserData;
import Model.Appointment;
import Model.Customer;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * Controller for showing reports to user
 * 
 * @author Hosea Gibson
 */
public class ReportController implements Initializable {
    
    // Variables
    @FXML
    private ToggleGroup tgReports;
    
    @FXML
    private RadioButton tbApptTypes;

    @FXML
    private RadioButton tbSchedule;

    @FXML
    private RadioButton tbCustomer;
    
    @FXML
    private TextArea txtReportField;
    
    @FXML
    private Button btnExit;
    
    @FXML
    private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    @FXML
    private final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("hh:mm a z");

    // Method to show appointments reports by the type
    @FXML
    void onClickAppTypes(ActionEvent event) {
        if (tbApptTypes.isSelected()) {
            try {
                txtReportField.clear();
                ObservableList<Appointment> types = AppointmentData.getApptsByMonth();
                Integer value = 1;
                Map<String, Integer> map = new HashMap<>();
                for (Appointment a : types) {
                    if (map.containsKey(a.getType())) {
                        map.put(a.getType(), map.get(a.getType())+1);
                    }
                    else{
                        map.put(a.getType(), value);
                    }
                }
                for (String s : map.keySet()) {
                    txtReportField.appendText("There are: " + map.get(s) + " appointment type(s) of: " + s + " this month.\n");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Method to show reports of list of Customers
    @FXML
    void onClickCustomersList(ActionEvent event) {
        if (tbCustomer.isSelected()) {
            try {
                txtReportField.clear();
                ObservableList<Customer> showCust = CustomerData.getAllCustomers();
                String customerName = "";
                Boolean active;
                String newline = "\n";
                for (Customer c : showCust) {
                    customerName = c.getCustomerName();
                    active = c.activeProperty().get();
                    txtReportField.appendText("Customer: " + customerName + " active status equals: " + active + " in the database." + newline);
                }
                
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Method to show report schedules 
    @FXML
    void onClickSchedule(ActionEvent event) {
        if (tbSchedule.isSelected()) {
            try {
                txtReportField.clear();
                ObservableList<Appointment> showSched = AppointmentData.getApptsByUser();
                String userName = "";
                String custName = "";
                ZonedDateTime startZDT;
                
                for (Appointment a : showSched) {
                    int u = a.getUserId();
                    int c = a.getCustomerId();
                    startZDT = a.getStart();
                    User userById = UserData.getUserById(u);
                    userName = userById.getUserName();
                    Customer activeCustomerById = CustomerData.getActiveCustomerById(c);
                    custName = activeCustomerById.getCustomerName();
                    txtReportField.appendText("User: " + userName 
                            + " has an appointment with " + custName 
                            + " on " + startZDT.format(formatDate) 
                            + " at " + startZDT.format(formatTime) + ".\n");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // Method to exit screen
    @FXML
    void onClickExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
