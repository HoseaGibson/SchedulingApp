package ViewController;

import DBManager.AppointmentData;
import DBManager.CustomerData;
import Exception.AppointExcept;
import Model.Appointment;
import Model.Customer;
import static ViewController.LoginScreenController.Loggers;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

/**
 * FXML Controller class
 * 
 * Controller that allows user to add a appointment to customer
 * 
 * @author Hosea Gibson
 */
public class AddAppointmentController implements Initializable {
    // Variables
    @FXML
    private Label lblCustomer;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblType;

    @FXML
    private Label lblUrl;

    @FXML
    private Label lblStart;

    @FXML
    private Label lblEnd;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtUrl;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnExit;

    @FXML
    private ComboBox<Customer> cbCustomer;
    
    @FXML
    private DatePicker dpStartDate;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private Spinner<LocalTime> spStartTime;

    @FXML
    private Spinner<LocalTime> spEndTime;
    
    @FXML
    public static Appointment appt = new Appointment();
    
    @FXML
    private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @FXML
    private final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
    
    @FXML
    private static ZoneId zId = ZoneId.systemDefault();

    /*
        Method for button to exit add appointment screen
    */
    @FXML
    void onClickExit(ActionEvent eExitAction) {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit Scheduling App");
        exitAlert.setHeaderText("Are you sure you want to exit?");
        exitAlert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        exitAlert.showAndWait();
        if (exitAlert.getResult() == ButtonType.OK) {
            try {
                FXMLLoader aCLoader = new FXMLLoader(AppointmentCalendarController.class.getResource("AppointmentCalendar.fxml"));
                Parent aCScreen = aCLoader.load();
                Scene aCScene = new Scene(aCScreen);
                Stage aCStage = new Stage();
                aCStage.setTitle("Appointment Calendar");
                aCStage.setScene(aCScene);
                aCStage.show();
                Stage addApptStage = (Stage) btnExit.getScene().getWindow();
                addApptStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            exitAlert.close();
        }
    }

    /*
        Method for button to save customer
    */
    @FXML
    void onClickSave(ActionEvent eSaveAction) throws Exception {
        Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
        saveAlert.setTitle("Save Appointment Modifications");
        saveAlert.setHeaderText("Are you sure you want to save?");
        saveAlert.setContentText("Press OK to save the addition. \nPress Cancel to stay on this screen.");
        saveAlert.showAndWait();
        if (saveAlert.getResult() == ButtonType.OK) {
            try {
                getApptInfo();
                appt.isValidInput();
                appt.isNotOverlapping();
                if (appt.isValidInput() && appt.isNotOverlapping()) {
                    AppointmentData.addAppointment(appt);
                    FXMLLoader aCLoader = new FXMLLoader(AppointmentCalendarController.class.getResource("AppointmentCalendar.fxml"));
                    Parent aCScreen = aCLoader.load();
                    Scene aCScene = new Scene(aCScreen);
                    Stage aCStage = new Stage();
                    aCStage.setTitle("Appointment Calendar");
                    aCStage.setScene(aCScene);
                    aCStage.show();
                    Stage addApptStage = (Stage) btnSave.getScene().getWindow();
                    addApptStage.close();
                }
            }
            catch (AppointExcept e) {
                Alert exAlert = new Alert(Alert.AlertType.ERROR);
                exAlert.setTitle("Overlapping Exception");
                exAlert.setHeaderText("There was an exception!");
                exAlert.setContentText(e.getMessage());
                exAlert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        }
        else {
            saveAlert.close();
        }
    }
    
    public void getApptInfo() throws AppointExcept {
        try {
            appt.setCustomer(cbCustomer.getValue());
            appt.setCustomerId(cbCustomer.getValue().getCustomerId());
            appt.setUserId(Loggers.getUserId());
            appt.setTitle(txtTitle.getText());
            appt.setDescription(txtDescription.getText());
            appt.setLocation(txtLocation.getText());
            appt.setContact(txtContact.getText());
            appt.setType(txtType.getText());
            appt.setUrl(txtUrl.getText());
            appt.setStart(ZonedDateTime.of(LocalDate.parse(dpStartDate.getValue().toString(), formatDate), LocalTime.parse(spStartTime.getValue().toString(), formatTime), zId));
            appt.setEnd(ZonedDateTime.of(LocalDate.parse(dpEndDate.getValue().toString(), formatDate), LocalTime.parse(spEndTime.getValue().toString(), formatTime), zId));
        }
        catch (NullPointerException e) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Appointment Addition Error");
            nullAlert.setHeaderText("The appointment is not able to be added!");
            nullAlert.setContentText("You must select a customer!");
            nullAlert.showAndWait();
        }
    }
    
    public void getActiveCustomers() {
        ObservableList<Customer> activeCusts = CustomerData.getActiveCustomers();
        cbCustomer.setItems(activeCusts);
        cbCustomer.setPromptText("Select a customer:");
    }
    
    public void convertCustomerString() {
        cbCustomer.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer cust) {
                return cust.getCustomerName();
            }

            @Override
            public Customer fromString(String string) {
                return cbCustomer.getValue();
            }
        });
    }
    
    public void setDefaultDateTime() {
        dpStartDate.setValue(LocalDate.now());
        dpEndDate.setValue(LocalDate.now());
        spStartTime.setValueFactory(svfStart);
        svfStart.setValue(LocalTime.of(8, 00));
        spEndTime.setValueFactory(svfEnd);
        svfEnd.setValue(LocalTime.of(17, 00));
    }
    
    SpinnerValueFactory svfStart = new SpinnerValueFactory<LocalTime>() {
        {
            setConverter(new LocalTimeStringConverter(formatTime,null));
        }
        @Override public void decrement(int steps) {
            LocalTime time = (LocalTime) getValue();
            setValue(time.minusHours(steps));
            setValue(time.minusMinutes(16 - steps));
        }
        @Override public void increment(int steps) {
            LocalTime time = (LocalTime) getValue();
            setValue(time.plusHours(steps));
            setValue(time.plusMinutes(steps + 14));
        }
    };
    
    SpinnerValueFactory svfEnd = new SpinnerValueFactory<LocalTime>() {
        {
            setConverter(new LocalTimeStringConverter(formatTime,null));
        }
        @Override
        public void decrement(int steps) {
            LocalTime time = (LocalTime) getValue();
            setValue(time.minusHours(steps));
            setValue(time.minusMinutes(16 - steps));
        }
        @Override
        public void increment(int steps) {
            LocalTime time = (LocalTime) getValue();
            setValue(time.plusHours(steps));
            setValue(time.plusMinutes(steps + 14));
        }
    };

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getActiveCustomers();
        convertCustomerString();
        setDefaultDateTime();
    }    
    
}
