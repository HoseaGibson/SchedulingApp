package ViewController;

import DBManager.AppointmentData;
import DBManager.CustomerData;
import Exception.AppointExcept;
import Model.Appointment;
import Model.Customer;
import static ViewController.AppointmentCalendarController.appointSelection;

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
 * @author Hosea Gibson
 */
public class ModifyAppointmentController implements Initializable {
    
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
    private ComboBox<Customer> cbCustomer;

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
    private TextField txtStart;

    @FXML
    private TextField txtEnd;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnExit;
    
    @FXML
    private DatePicker dpStartDate;

    @FXML
    private Spinner<LocalTime> spStartTime;
    
    @FXML
    private DatePicker dpEndDate;

    @FXML
    private Spinner<LocalTime> spEndTime;
    
    @FXML
    private String errEmptyInput = new String();
    
    @FXML
    private String errValidation = new String();
    
    @FXML
    public static Appointment appt = new Appointment();
    
    @FXML
    private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @FXML
    private final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
    
    @FXML
    private static ZoneId zId = ZoneId.systemDefault();
    
    // Method to Exit Modify appointment Screen
    @FXML
    void onClickExit(ActionEvent eExitAction) {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit Modify Appointment");
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
                Stage modStage = (Stage) btnExit.getScene().getWindow();
                modStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            exitAlert.close();
        }
    }

    // Method to save customer to
    @FXML
    void onClickSave(ActionEvent eSaveAction) throws Exception {
        Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
        saveAlert.setTitle("Save Appointment Modifications");
        saveAlert.setHeaderText("Are you sure you want to save?");
        saveAlert.setContentText("Press OK to save the modifications. \nPress Cancel to stay on this screen.");
        saveAlert.showAndWait();
        if (saveAlert.getResult() == ButtonType.OK) {
            try {
                updateApptInfo();
                appt.isValidInput();
                appt.isNotOverlapping();
                if (appt.isValidInput() && appt.isNotOverlapping()) {
                    AppointmentData.updateAppointment(appt);
                    FXMLLoader aCLoader = new FXMLLoader(AppointmentCalendarController.class.getResource("AppointmentCalendar.fxml"));
                    Parent aCScreen = aCLoader.load();
                    Scene aCScene = new Scene(aCScreen);
                    Stage aCStage = new Stage();
                    aCStage.setTitle("Appointment Calendar");
                    aCStage.setScene(aCScene);
                    aCStage.show();
                    Stage modApptStage = (Stage) btnSave.getScene().getWindow();
                    modApptStage.close();
                }
            }
            catch (AppointExcept e) {
                Alert exAlert = new Alert(Alert.AlertType.ERROR);
                exAlert.setTitle("Exception");
                exAlert.setHeaderText("There was an exception!");
                exAlert.setContentText(e.getMessage());
                exAlert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        }
        else {
            saveAlert.close();
        }
    }
    
    private ObservableList<Customer> activeCust = CustomerData.getActiveCustomers();
    
    public void getActiveCustomers() {
        cbCustomer.setItems(activeCust);
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
    
    public void setSelectedApptInfo() {
        cbCustomer.setValue(appointSelection.getCustomer());
        txtTitle.setText(appointSelection.getTitle());
        txtDescription.setText(appointSelection.getDescription());
        txtLocation.setText(appointSelection.getLocation());
        txtContact.setText(appointSelection.getContact());
        txtType.setText(appointSelection.getType());
        txtUrl.setText(appointSelection.getUrl());
        dpStartDate.setValue(appointSelection.getStart().toLocalDate());
        spStartTime.setValueFactory(svfStart);
        svfStart.setValue(appointSelection.getStart().toLocalTime());
        dpEndDate.setValue(appointSelection.getEnd().toLocalDate());
        spEndTime.setValueFactory(svfEnd);
        svfEnd.setValue(appointSelection.getEnd().toLocalTime());
    }
    
    public void updateApptInfo() {
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
        appt.setAppointmentId(appointSelection.getAppointmentId());
    }

    SpinnerValueFactory svfStart = new SpinnerValueFactory<LocalTime>() {
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
        setSelectedApptInfo();
        convertCustomerString();
    }
    
}
