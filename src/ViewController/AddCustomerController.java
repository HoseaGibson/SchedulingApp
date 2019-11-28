package ViewController;

import DBManager.AddressData;
import DBManager.CityData;
import DBManager.CountryData;
import DBManager.CustomerData;
import Exception.CustExcep;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
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
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 * 
 * Controller to allow the user to add a new customer to the database
 * 
 * @author Hosea Gibson
 */
public class AddCustomerController implements Initializable {
    
    // Variables
    @FXML
    private Label lblCustName;

    @FXML
    private Label lblCustAddress;

    @FXML
    private Label lblCustAddress2;

    @FXML
    private Label lblCity;

    @FXML
    private Label lblCountry;

    @FXML
    private Label lblCustPostalCode;

    @FXML
    private Label lblCustPhone;

    @FXML
    private TextField txtCustName;

    @FXML
    private TextField txtCustAddress;

    @FXML
    private TextField txtCustAddress2;

    @FXML
    private TextField txtCustCity;

    @FXML
    private ComboBox<Country> cbCountry;

    @FXML
    private TextField txtCustPostalCode;

    @FXML
    private TextField txtCustPhone;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnExit;
    
    @FXML
    private Customer newCust = new Customer();
    private Address custAddress = new Address();
    private City custCity = new City();
    private Country custCountry = new Country();

    /*
        Method for button to exit add customer screen
    */
    @FXML
    void onClickExit(ActionEvent eExit) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Add Customer ");
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

    /*
        Method for button to save the customer information enter to database
    */
    @FXML
    void onClickSave(ActionEvent event) {
        Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
        saveAlert.setTitle("Save Customer Modifications");
        saveAlert.setHeaderText("Are you sure you want to save?");
        saveAlert.setContentText("Press OK to save the modifications. \nPress Cancel to stay on this screen.");
        saveAlert.showAndWait();
        if (saveAlert.getResult() == ButtonType.OK) {
            try {
                getCustInfo();
                if (Customer.isValidInput(newCust, custAddress, custCity, custCountry)) {
                    try {
                        CityData.addCity(custCity);
                        custAddress.setCityId(CityData.getCityId(custCity.getCity()));
                        AddressData.addAddress(custAddress);
                        newCust.setAddressId(AddressData.getAddressId(custAddress.getAddress()));
                        CustomerData.addCustomer(newCust);
                        FXMLLoader aCLoader = new FXMLLoader(AppointmentCalendarController.class.getResource("AppointmentCalendar.fxml"));
                        Parent aCScreen = aCLoader.load();
                        Scene aCScene = new Scene(aCScreen);
                        Stage aCStage = new Stage();
                        aCStage.setTitle("Appointment Calendar");
                        aCStage.setScene(aCScene);
                        aCStage.show();
                        Stage addCustStage = (Stage) btnSave.getScene().getWindow();
                        addCustStage.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (CustExcep e) {
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
    
    public void getCustInfo() {
        try {
            newCust.setCustomerName(txtCustName.getText());
            custAddress.setAddress(txtCustAddress.getText());
            custAddress.setAddress2(txtCustAddress2.getText());
            custAddress.setPostalCode(txtCustPostalCode.getText());
            custAddress.setPhone(txtCustPhone.getText());
            custCity.setCity(txtCustCity.getText());
            custCity.setCountryId(cbCountry.getSelectionModel().getSelectedItem().getCountryId());
            custCountry.setCountry(cbCountry.getSelectionModel().getSelectedItem().getCountry());
        }
        catch (NullPointerException e) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Customer Addition Error");
            nullAlert.setHeaderText("The customer is not able to be added!");
            nullAlert.setContentText("You must select a country!");
            nullAlert.showAndWait();
        }
    }
    
     public void setCountries() {
        cbCountry.setItems(CountryData.getAllCountries());
    }
    
    public void convertCountryString() {
        cbCountry.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country.getCountry();
            }

            @Override
            public Country fromString(String string) {
                return cbCountry.getValue();
            }
        });
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        convertCountryString();
        setCountries();
    }    
    
}
