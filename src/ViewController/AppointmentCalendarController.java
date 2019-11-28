package ViewController;

import DBManager.AppointmentData;
import static DBManager.AppointmentData.getApptsByMonth;
import static DBManager.AppointmentData.getApptsByWeek;
import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 *  Controller to show all appointments 
 *  It allow users to see monthly and weekly views for customer
 *  This acts as the main screen and allow users to add, modify
 *  delete, see logs, reports for appointments and customers
 */
public class AppointmentCalendarController implements Initializable {
    // variables
    @FXML
    private Button btnNewAppt;

    @FXML
    private Button btnModifyAppt;

    @FXML
    private Button btnDeleteAppt;

    @FXML
    private Button btnNewCust;

    @FXML
    private Button btnModifyCust;

    @FXML
    private Button btnDeleteCust;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnUserLogs;

    @FXML
    private Button btnExit;

    @FXML
    private Tab tpWeeklyAppts;

    @FXML
    private TableView<Appointment> tvWeeklyAppts;

    @FXML
    private TableColumn<Appointment, String> tcWeeklyCustName;

    @FXML
    private TableColumn<Appointment, String> tcWeeklyApptTitle;

    @FXML
    private TableColumn<Appointment, String> tcWeeklyApptDescription;

    @FXML
    private TableColumn<Appointment, String> tcWeeklyApptLocation;

    @FXML
    private TableColumn<Appointment, String> tcWeeklyApptContact;

    @FXML
    private TableColumn<Appointment, String> tcWeeklyApptType;

    @FXML
    private TableColumn<Appointment, String> tcWeeklyApptURL;

    @FXML
    private TableColumn<Appointment, String> tcWeeklyApptStart;

    @FXML
    private TableColumn<Appointment, String> tcWeeklyApptEnd;

    @FXML
    private Tab tpMonthlyAppts;

    @FXML
    private TableView<Appointment> tvMonthlyAppts;

    @FXML
    private TableColumn<Appointment, String> tcMonthlyCustName;

    @FXML
    private TableColumn<Appointment, String> tcMonthlyApptTitle;

    @FXML
    private TableColumn<Appointment, String> tcMonthlyApptDescription;

    @FXML
    private TableColumn<Appointment, String> tcMonthlyApptLocation;

    @FXML
    private TableColumn<Appointment, String> tcMonthlyApptContact;

    @FXML
    private TableColumn<Appointment, String> tcMonthlyApptType;

    @FXML
    private TableColumn<Appointment, String> tcMonthlyApptURL;

    @FXML
    private TableColumn<Appointment, String> tcMonthlyApptStart;

    @FXML
    private TableColumn<Appointment, String> tcMonthlyApptEnd;
    
    @FXML
    private final DateTimeFormatter formatDT = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a z");
    
     // Used to check if timezones are correct 
    //private final DateTimeFormatter formatDT = DateTimeFormatter.ISO_ZONED_DATE_TIME;
    
    @FXML
    public static Appointment appointSelection;
    
    @FXML
    public static boolean isMod;
    
    /*
        Method for Button to add a new Appointment
        Will take user to add appointment screen
    */
    @FXML
    void onClickNewAppoint(ActionEvent event) {
        Alert addAlert = new Alert(AlertType.CONFIRMATION);
        addAlert.setTitle("Add Appointment");
        addAlert.setHeaderText("Are you sure you want to add a new appointment?");
        addAlert.setContentText("Press OK to add the appointment. \nPress Cancel to cancel the addition.");
        addAlert.showAndWait();
        if (addAlert.getResult() == ButtonType.OK) {
            try {
                FXMLLoader addApptLoader = new FXMLLoader(AddAppointmentController.class.getResource("AddAppointment.fxml"));
                Parent addApptScreen = addApptLoader.load();
                Scene addApptScene = new Scene(addApptScreen);
                Stage addApptStage = new Stage();
                addApptStage.setTitle("Add Appointment");
                addApptStage.setScene(addApptScene);
                addApptStage.setResizable(false);
                addApptStage.show();
                Stage apptCalStage = (Stage) btnNewAppt.getScene().getWindow();
                apptCalStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /*
        Method for button modify appointments
        Sends user to modify appointment screen
    */
    @FXML
    void onClickModAppoint(ActionEvent event) {
        if (tpWeeklyAppts.isSelected()) {
            appointSelection = tvWeeklyAppts.getSelectionModel().getSelectedItem();
            if (appointSelection == null) {
                Alert nullAlert = new Alert(AlertType.ERROR);
                nullAlert.setTitle("Appointment Modification Error");
                nullAlert.setHeaderText("The appointment is not able to be modified!");
                nullAlert.setContentText("There was no appointment selected!");
                nullAlert.showAndWait();
            }
            else {
                    Alert modAlert = new Alert(AlertType.CONFIRMATION);
                    modAlert.setTitle("Modify Appointment");
                    modAlert.setHeaderText("Are you sure you want to modify this appointment?");
                    modAlert.setContentText("Press OK to modify the appointment. \nPress Cancel to cancel the modification.");
                    modAlert.showAndWait();
                    if (modAlert.getResult() == ButtonType.OK) {
                        try {
                            FXMLLoader modApptLoader = new FXMLLoader(ModifyAppointmentController.class.getResource("ModifyAppointment.fxml"));
                            Parent modApptScreen = modApptLoader.load();
                            Scene modApptScene = new Scene(modApptScreen);
                            Stage modApptStage = new Stage();
                            modApptStage.setTitle("Modify Appointment");
                            modApptStage.setScene(modApptScene);
                            modApptStage.setResizable(false);
                            modApptStage.show();
                            Stage apptCalStage = (Stage) btnModifyAppt.getScene().getWindow();
                            apptCalStage.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        else if (tpMonthlyAppts.isSelected()) {
            appointSelection = tvMonthlyAppts.getSelectionModel().getSelectedItem();
            if (appointSelection == null) {
                Alert nullAlert = new Alert(AlertType.ERROR);
                nullAlert.setTitle("Appointment Modification Error");
                nullAlert.setHeaderText("The appointment is not able to be modified!");
                nullAlert.setContentText("There was no appointment selected!");
                nullAlert.showAndWait();
            }
            else {
                    Alert modAlert = new Alert(AlertType.CONFIRMATION);
                    modAlert.setTitle("Modify Appointment");
                    modAlert.setHeaderText("Are you sure you want to modify this appointment?");
                    modAlert.setContentText("Press OK to modify the appointment. \nPress Cancel to cancel the modification.");
                    modAlert.showAndWait();
                    if (modAlert.getResult() == ButtonType.OK) {
                        try {
                            FXMLLoader modLoader = new FXMLLoader(ModifyAppointmentController.class.getResource("ModifyAppointment.fxml"));
                            Parent modScreen = modLoader.load();
                            Scene modScene = new Scene(modScreen);
                            Stage modStage = new Stage();
                            modStage.setTitle("Modify Appointment");
                            modStage.setScene(modScene);
                            modStage.setResizable(false);
                            modStage.show();
                            Stage apptCalStage = (Stage) btnModifyAppt.getScene().getWindow();
                            apptCalStage.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }
    
     /*
        Method for button delete appointments       
    */
    @FXML
    void onClickDeleteAppoint(ActionEvent event) {
        if (tpWeeklyAppts.isSelected()) {
            Alert delAlert = new Alert(AlertType.CONFIRMATION);
            delAlert.setTitle("Delete Appointment");
            delAlert.setHeaderText("Are you sure you want to delete this appointment?");
            delAlert.setContentText("Press OK to delete the appointment. \nPress Cancel to cancel the deletion.");
            delAlert.showAndWait();
            if (delAlert.getResult() == ButtonType.OK) {
                try {
                    Appointment appt = tvWeeklyAppts.getSelectionModel().getSelectedItem();
                    AppointmentData.deleteAppointment(appt);
                    getAppointments();
                }
                catch (NullPointerException e) {
                    Alert nullAlert = new Alert(AlertType.ERROR);
                    nullAlert.setTitle("Appointment Modification Error");
                    nullAlert.setHeaderText("The appointment is not able to be deleted!");
                    nullAlert.setContentText("There was no appointment selected!");
                    nullAlert.showAndWait();
                }
            }
            else {
                delAlert.close();
            }
        }
        else if (tpMonthlyAppts.isSelected()) {
            Alert delAlert = new Alert(AlertType.CONFIRMATION);
            delAlert.setTitle("Delete Appointment");
            delAlert.setHeaderText("Are you sure you want to delete this appointment?");
            delAlert.setContentText("Press OK to delete the appointment. \nPress Cancel to cancel the deletion.");
            delAlert.showAndWait();
            if (delAlert.getResult() == ButtonType.OK) {
                try {
                    Appointment appt = tvMonthlyAppts.getSelectionModel().getSelectedItem();
                    AppointmentData.deleteAppointment(appt);
                    getAppointments();
                }
                catch (NullPointerException e) {
                    Alert nullAlert = new Alert(AlertType.ERROR);
                    nullAlert.setTitle("Appointment Modification Error");
                    nullAlert.setHeaderText("The appointment is not able to be deleted!");
                    nullAlert.setContentText("There was no appointment selected!");
                    nullAlert.showAndWait();
                }
            }
            else {
                delAlert.close();
            }
        }
    }

    /*
        Method for button add new customer
        Sends user to add a new customer screen 
    */
    @FXML
    void onClickNewCust(ActionEvent event) {
        Alert addAlert = new Alert(AlertType.CONFIRMATION);
        addAlert.setTitle("Add Customer");
        addAlert.setHeaderText("Are you sure you want to add a new customer?");
        addAlert.setContentText("Press OK to add the customer. \nPress Cancel to cancel the addition.");
        addAlert.showAndWait();
        if (addAlert.getResult() == ButtonType.OK) {
            try {
                FXMLLoader addCLoader = new FXMLLoader(AddCustomerController.class.getResource("AddCustomer.fxml"));
                Parent addCScreen = addCLoader.load();
                Scene addCScene = new Scene(addCScreen);
                Stage addCStage = new Stage();
                addCStage.setTitle("Add Customer");
                addCStage.setScene(addCScene);
                addCStage.setResizable(false);
                addCStage.show();
                Stage aCStage = (Stage) btnNewCust.getScene().getWindow();
                aCStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
      /*
        Method for button modify customer
        Sends user to modify customer screen
    */
    @FXML
    void onClickModCust(ActionEvent event) {
        Alert modAlert = new Alert(AlertType.CONFIRMATION);
        modAlert.setTitle("Modify Customer");
        modAlert.setHeaderText("Are you sure you want to modify a customer?");
        modAlert.setContentText("Press OK to modify a customer. \nPress Cancel to cancel the modification.");
        modAlert.showAndWait();
        if (modAlert.getResult() == ButtonType.OK) {
            try {
                isMod=true;
                FXMLLoader selCLoader = new FXMLLoader(CustomerSelectionController.class.getResource("CustomerSelection.fxml"));
                Parent selCScreen = selCLoader.load();
                Scene selCScene = new Scene(selCScreen);
                Stage selCStage = new Stage();
                selCStage.setTitle("Customer Selection");
                selCStage.setScene(selCScene);
                selCStage.setResizable(false);
                selCStage.show();
                Stage aCStage = (Stage) btnModifyCust.getScene().getWindow();
                aCStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            modAlert.close();
        }
    }     
    
    /*
        Method for button delete customers 
    */
    @FXML
    void onClickDeleteCust(ActionEvent event) {
        Alert delAlert = new Alert(AlertType.CONFIRMATION);
        delAlert.setTitle("Delete Customer");
        delAlert.setHeaderText("Are you sure you want to delete a customer?");
        delAlert.setContentText("Press OK to delete a customer. \nPress Cancel to cancel the deletion.");
        delAlert.showAndWait();
        if (delAlert.getResult() == ButtonType.OK) {
            try {
                isMod=false;
                FXMLLoader selCLoader = new FXMLLoader(CustomerSelectionController.class.getResource("CustomerSelection.fxml"));
                Parent selCScreen = selCLoader.load();
                Scene selCScene = new Scene(selCScreen);
                Stage selCStage = new Stage();
                selCStage.setTitle("Customer Selection");
                selCStage.setScene(selCScene);
                selCStage.setResizable(false);
                selCStage.show();
                Stage aCStage = (Stage) btnDeleteCust.getScene().getWindow();
                aCStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            delAlert.close();
        }
    }

    /*
        Method for button to go to reports screen
    */
    @FXML
    void onClickReport(ActionEvent event) {
        try {
            FXMLLoader runReportLoader = new FXMLLoader(ReportController.class.getResource("Report.fxml"));
            Parent reportScreen = runReportLoader.load();
            Scene runReportScene = new Scene(reportScreen);
            Stage reportStage = new Stage();
            reportStage.setTitle("Reports");
            reportStage.setScene(runReportScene);
            reportStage.setResizable(false);
            reportStage.show();
            Stage aCStage = (Stage) btnModifyCust.getScene().getWindow();
            aCStage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickLogs(ActionEvent event) {
        try {
            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "userlog.txt");
            pb.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
        Method for button to go to Logs screen
    */
    @FXML
    void onClickExit(ActionEvent eExitButton) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Scheduling App");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage winMainScreen = (Stage)((Node)eExitButton.getSource()).getScene().getWindow();
            winMainScreen.close();
        }
        else {
            alert.close();
        }
    }
    
    public void getAppointments() {
        //Multiple lambdas to effciently set the values of the weekly and monthly table view columns.
        tcWeeklyCustName.setCellValueFactory(cellData -> { return cellData.getValue().getCustomer().customerNameProperty(); });
        tcWeeklyApptTitle.setCellValueFactory(cellData -> { return cellData.getValue().titleProperty(); });
        tcWeeklyApptDescription.setCellValueFactory(cellData -> { return cellData.getValue().descriptionProperty(); });
        tcWeeklyApptLocation.setCellValueFactory(cellData -> { return cellData.getValue().locationProperty(); });
        tcWeeklyApptContact.setCellValueFactory(cellData -> { return cellData.getValue().contactProperty(); });
        tcWeeklyApptType.setCellValueFactory(cellData -> { return cellData.getValue().typeProperty(); });
        tcWeeklyApptURL.setCellValueFactory(cellData -> { return cellData.getValue().urlProperty(); });
        tcWeeklyApptStart.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(formatDT)));
        tcWeeklyApptEnd.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getEnd().format(formatDT)));
        tvWeeklyAppts.setItems(getApptsByWeek());
        tcMonthlyCustName.setCellValueFactory(cellData -> { return cellData.getValue().getCustomer().customerNameProperty(); });
        tcMonthlyApptTitle.setCellValueFactory(cellData -> { return cellData.getValue().titleProperty(); });
        tcMonthlyApptDescription.setCellValueFactory(cellData -> { return cellData.getValue().descriptionProperty(); });
        tcMonthlyApptLocation.setCellValueFactory(cellData -> { return cellData.getValue().locationProperty(); });
        tcMonthlyApptContact.setCellValueFactory(cellData -> { return cellData.getValue().contactProperty(); });
        tcMonthlyApptType.setCellValueFactory(cellData -> { return cellData.getValue().typeProperty(); });
        tcMonthlyApptURL.setCellValueFactory(cellData -> { return cellData.getValue().urlProperty(); });
        tcMonthlyApptStart.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(formatDT)));
        tcMonthlyApptEnd.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getEnd().format(formatDT)));
        tvMonthlyAppts.setItems(getApptsByMonth());
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getAppointments();
        
    }
       
}
